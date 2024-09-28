package ensias.ma.gl.secondyear.twentyfour.econutri.visitor;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Merchant;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;

@Component
public class OrderApprovalPermissionVerifier extends OrderTransitionPermissionVerifier {
    
    public boolean canMerchantInduceOrderTransition(Merchant merchant, Order order) {
        return order.getOffer().getPublisher().equals(merchant);
    }
}
