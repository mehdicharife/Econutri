package ensias.ma.gl.secondyear.twentyfour.econutri.web.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.NonClientOrderCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.mapper.OrderMapper;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.OrderCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.response.OrderResponse;
import ensias.ma.gl.secondyear.twentyfour.econutri.service.OrderService;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    private OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }


    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreationRequest orderRequest, Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();
        
        try {
            Order order = this.orderService.createOrder(user, orderRequest);
            OrderResponse orderDto = this.orderMapper.toDto(order);
            return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
            
        } catch(NonClientOrderCreationRequest ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(Authentication auth) {
        
        User user = (User) auth.getPrincipal();
        
        var ordersDtos = this.orderService.getOrders(user)
            .stream()
            .map(order -> this.orderMapper.toDto(order))
            .toList();
            
        return ResponseEntity.ok(ordersDtos);
    }


    
}
