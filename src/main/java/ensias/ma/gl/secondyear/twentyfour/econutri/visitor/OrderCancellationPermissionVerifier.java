package ensias.ma.gl.secondyear.twentyfour.econutri.visitor;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Client;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;


@Component
public class OrderCancellationPermissionVerifier extends OrderTransitionPermissionVerifier {

    
    @Override
    public boolean canClientInduceOrderTransition(Client client, Order order) {
        return order.getOrderer().equals(client);
    }
    
}
