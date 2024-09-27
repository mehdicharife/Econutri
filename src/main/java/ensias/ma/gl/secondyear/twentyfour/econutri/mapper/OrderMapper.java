package ensias.ma.gl.secondyear.twentyfour.econutri.mapper;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;
import ensias.ma.gl.secondyear.twentyfour.econutri.response.OrderResponse;

@Component
public class OrderMapper {

    public OrderResponse toDto(Order order ){
        
        OrderResponse dto = new OrderResponse();
        
        dto.setId(order.getId());
        dto.setClientId(order.getOrderer().getId());
        dto.setOfferId(order.getOffer().getId());
        dto.setQuantity(order.getProductQuantity());

        return dto;
    }
    
}
