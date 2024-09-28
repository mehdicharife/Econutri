package ensias.ma.gl.secondyear.twentyfour.econutri.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.BadPropertiesException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.NonClientOrderCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.OfferIdNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.OrderCreationException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.UnauthorizedOrderTransitionException;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Client;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Offer;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.OrderState;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.repository.OrderRepository;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.OrderCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.SimpleOrderTransitionRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.OrderApprovalPermissionVerifier;
import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.OrderCancellationPermissionVerifier;
import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.OrderRejectionPermissionVerifier;
import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.OrderTransitionPermissionVerifier;
import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.UserRoleGetter;


@Component
public class OrderService {
    
    private OrderRepository orderRepository;

    private UserBasedOrdersRetriever ordersRetriever;

    private UserRoleGetter userRoleGetter;

    private OfferService offerService;

    private OrderApprovalPermissionVerifier approvalVerifier;
    private OrderRejectionPermissionVerifier rejectionVerifier;
    private OrderCancellationPermissionVerifier cancellationVerifier;


    public OrderService(OrderRepository orderRepository, 
                        UserBasedOrdersRetriever ordersRetriever,
                        OfferService offerService, 
                        UserRoleGetter userRoleGetter,  
                        OrderApprovalPermissionVerifier approvalVerifier,
                        OrderRejectionPermissionVerifier rejectionVerifier,
                        OrderCancellationPermissionVerifier cancellationVerifier
                        ) {

        this.orderRepository = orderRepository;
        this.ordersRetriever = ordersRetriever;
        this.offerService = offerService;
        this.userRoleGetter = userRoleGetter;
        this.approvalVerifier = approvalVerifier;
        this.rejectionVerifier = rejectionVerifier;
        this.cancellationVerifier = cancellationVerifier;
        
    }



    public List<Order> getOrders(User requester) {
        return this.ordersRetriever.getOrders(requester);
    }


    public Order createOrder(User requester, OrderCreationRequest orderRequest) 
        throws OrderCreationException, NonClientOrderCreationRequest {
        
        String role = userRoleGetter.getUserRole(requester);
        if(!"CLIENT".equals(role)) {
            throw new NonClientOrderCreationRequest(requester);
        }

        Offer offer = validateOrderCreationRequest(orderRequest);

        Order order = new Order();
        order.setOffer(offer);
        order.setOrderer((Client) requester);
        order.setProductQuantity(orderRequest.getProductQuantity());

        return orderRepository.save(order);
    }


    public Order changeOrderState(
        SimpleOrderTransitionRequest orderTransitionRequest, 
        User user) throws BadPropertiesException, UnauthorizedOrderTransitionException {

            Order order = validateOrderTransitionRequest(orderTransitionRequest, user);
            order.setState(OrderState.valueOf(orderTransitionRequest.getNewState()));

            return this.orderRepository.save(order);

    }

    
    private Offer validateOrderCreationRequest(OrderCreationRequest orderRequest) 
        throws OrderCreationException {

        Long offerId = orderRequest.getOfferId();
        Long desiredQuantity = orderRequest.getProductQuantity();
        Map<String, String> errorsMap = new HashMap<>();
        
        try {
            Offer offer = this.offerService.getOfferById(offerId);
            Long availableQuantity = offer.getAvailableQuantity();
            if(desiredQuantity > availableQuantity) {
                errorsMap.put("desiredQuantity", "The ordered quantity must be smaller than " + availableQuantity);
            }

            if(offer.getExpirationDate().before(new Date())) {
                errorsMap.put("offerId", "The offer identified by " + offerId + " is expired");
            }

            if(errorsMap.isEmpty()) {
                return offer;
            }

        } catch(OfferIdNotFoundException ex) {
            errorsMap.put("offerId", "There is no offer with the id "  + offerId);
        }

        throw new OrderCreationException(errorsMap);
    }


    public Order validateOrderTransitionRequest(
        SimpleOrderTransitionRequest orderTransitionRequest, 
        User user) throws BadPropertiesException, UnauthorizedOrderTransitionException {

        var transitionsPermissionsVerifiers = Map.of(
            OrderState.INITIAL, Map.of(
                OrderState.APPROVED,  approvalVerifier,
                OrderState.CANCELED, cancellationVerifier,
                OrderState.REJECTED, rejectionVerifier
            ),

            OrderState.APPROVED, Map.of(
                OrderState.REJECTED, rejectionVerifier,
                OrderState.CANCELED, cancellationVerifier
            )

        );

        String newStateAsStr = orderTransitionRequest.getNewState();
        Long orderId = orderTransitionRequest.getOrderId();
        Map<String, String> errors = new HashMap<>();

        try {
            OrderState.valueOf(newStateAsStr);
        } catch(IllegalArgumentException ex) {
            errors.put("newState", "There is no state called " + newStateAsStr);
        }

        Optional<Order> optionalOrder = this.orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()) {
            errors.put("orderId", "There is no order with the id " + orderId);
            throw new BadPropertiesException(errors);
        } 
        
        // The order exists but the new state doesn't
        if(!errors.isEmpty()) {
           throw new BadPropertiesException(errors);
        }

        // Both the order and the new state exist
        Order order = optionalOrder.get();
        OrderState newState = OrderState.valueOf(newStateAsStr);
        OrderState currentState = order.getState();


        var verifiersMap = transitionsPermissionsVerifiers.get(currentState);
        if(verifiersMap == null) {
            // Order state is either final (REJECTED or CANCELED, DELIVERED) or don't have simple 
            // transitions (PAYED_FOR, DELIVERED)
            if(currentState.compareTo(OrderState.PAYED_FOR) == 0) {
                errors.put("orderId", 
                    "The order identified by "  + orderId + " is in the state " + currentState.name() + " which " + 
                    "doesn't have simple transitions." 
                );
            } else {
                errors.put("orderId",
                    "The order with the id " + orderId + " is " + currentState.name() + " and thus " + 
                    "cannot become " + newState
                );
            }
        } else {
            OrderTransitionPermissionVerifier verifier = verifiersMap.get(newState);
            if(verifier == null) {
                errors.put("orderId, newState", 
                    "An order cannot transition from " + currentState.name() + " to " + newState.name()
                );
            } else {
                boolean canUserInduceTransition = verifier.canUserInduceOrderTransition(order, user);
                if(!canUserInduceTransition) {
                    throw new UnauthorizedOrderTransitionException(user, currentState, newState);
                } 
            }
        }

        if(!errors.isEmpty()) {
            throw new BadPropertiesException(errors);
        }

        return order;
    }

}
