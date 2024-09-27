package ensias.ma.gl.secondyear.twentyfour.econutri.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.NonClientOrderCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.OfferIdNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.OrderCreationException;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Client;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Offer;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.repository.OrderRepository;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.OrderCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.UserRoleGetter;


@Component
public class OrderService {
    
    private OrderRepository orderRepository;

    private UserRoleGetter userRoleGetter;

    private OfferService offerService;


    public OrderService(OrderRepository orderRepository, 
                        OfferService offerService, 
                        UserRoleGetter userRoleGetter) {

        this.orderRepository = orderRepository;
        this.offerService = offerService;
        this.userRoleGetter = userRoleGetter;
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


    public Offer validateOrderCreationRequest(OrderCreationRequest orderRequest) 
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
}
