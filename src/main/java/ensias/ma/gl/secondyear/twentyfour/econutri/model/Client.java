package ensias.ma.gl.secondyear.twentyfour.econutri.model;

import java.util.HashMap;
import java.util.Map;

import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.UserVisitor;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "clients")
public class Client extends User{
    
    
    @ElementCollection
    @CollectionTable(name = "cart", joinColumns = @JoinColumn(name = "client_id"))
    @MapKeyJoinColumn(name = "offer_id")
    private Map<Offer, Integer> cart = new HashMap<>();


    public Map<Offer,Integer> getCart() {
        return this.cart;
    }

    public void setCart(Map<Offer,Integer> cart) {
        this.cart = cart;
    }


    @Override
    public void accept(UserVisitor userVisitor) {
        userVisitor.visitClient(this);
    }

}
