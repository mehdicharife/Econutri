package ensias.ma.gl.secondyear.twentyfour.econutri.web.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.UnauthorizedOrderTransitionException;
import ensias.ma.gl.secondyear.twentyfour.econutri.mapper.OrderMapper;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.SimpleOrderTransitionRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.service.OrderService;


@RestController
@RequestMapping("/order-transitions")
public class SimpleOrderTransitionController {

    private OrderService orderService;

    private OrderMapper orderMapper;

    public SimpleOrderTransitionController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<?> createOrderTransition(
            @RequestBody SimpleOrderTransitionRequest orderTransitionRequest,
            Authentication auth) throws Exception {

        User user = (User) auth.getPrincipal();

        try {
            Order order = this.orderService.changeOrderState(orderTransitionRequest, user);
            return new ResponseEntity<>(this.orderMapper.toDto(order), HttpStatus.CREATED);
            
        } catch(UnauthorizedOrderTransitionException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
}
