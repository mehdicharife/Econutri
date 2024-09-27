package ensias.ma.gl.secondyear.twentyfour.econutri.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Offer offer;

    @ManyToOne
    private Client orderer;

    private Long productQuantity;

    private OrderState state = OrderState.INITIAL;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Offer getOffer() {
        return this.offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Client getOrderer() {
        return this.orderer;
    }

    public void setOrderer(Client orderer) {
        this.orderer = orderer;
    }

    public Long getProductQuantity() {
        return this.productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }


    public OrderState getState() {
        return this.state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

}
