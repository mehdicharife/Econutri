package ensias.ma.gl.secondyear.twentyfour.econutri.visitor;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Admin;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Client;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Merchant;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;


public abstract class OrderTransitionPermissionVerifier implements UserVisitor {

    private ThreadLocal<Boolean> canUserInduceTransition = new ThreadLocal<>();
    private ThreadLocal<Order> cachedOrder = new ThreadLocal<>();
    

    public final boolean canUserInduceOrderTransition(Order order, User user) {
        cachedOrder.set(order);
        user.accept(this);
        return canUserInduceTransition.get();
    }

    public final void visitAdmin(Admin admin) {
        boolean canUserInduceTransition = this.canAdminInduceOrderTransition(admin, cachedOrder.get());
        this.canUserInduceTransition.set(canUserInduceTransition);
    }

    public final void visitClient(Client client) {
        boolean canUserInduceTransition = this.canClientInduceOrderTransition(client, cachedOrder.get());
        this.canUserInduceTransition.set(canUserInduceTransition);
    }

    public final void visitMerchant(Merchant merchant) {
        boolean canUserInduceTransition = this.canMerchantInduceOrderTransition(merchant, cachedOrder.get());
        this.canUserInduceTransition.set(canUserInduceTransition);
    }


    public boolean canMerchantInduceOrderTransition(Merchant merchant, Order order) {
        return false;
    }

    public boolean canClientInduceOrderTransition(Client client, Order order) {
        return false;
    }

    public boolean canAdminInduceOrderTransition(Admin admin, Order order) {
        return false;
    }
}
