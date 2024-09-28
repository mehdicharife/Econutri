package ensias.ma.gl.secondyear.twentyfour.econutri.service;

import java.util.List;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Admin;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Client;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Merchant;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.repository.OrderRepository;
import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.UserVisitor;


@Component
public class UserBasedOrdersRetriever implements UserVisitor {

    private ThreadLocal<List<Order>> orders = new ThreadLocal<>();

    private OrderRepository orderRepository;


    public UserBasedOrdersRetriever(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    

    public List<Order> getOrders(User user) {
        user.accept(this);
        return this.orders.get();
    }


    @Override
    public void visitAdmin(Admin admin) {
        orders.set(List.of());
        
    }

    @Override
    public void visitClient(Client client) {
        var clientOrders = this.orderRepository.findByOrderer_Id(client.getId());
        orders.set(clientOrders);
        
    }

    @Override
    public void visitMerchant(Merchant merchant) {
        var merchantOrders = this.orderRepository.findByOffer_Publisher_Id(merchant.getId());
        orders.set(merchantOrders);
    }


    
}
