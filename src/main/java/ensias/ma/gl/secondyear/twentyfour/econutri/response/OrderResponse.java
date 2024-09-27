package ensias.ma.gl.secondyear.twentyfour.econutri.response;


public class OrderResponse {

    private Long id;
    
    private Long clientId;

    private Long offerId;

    private Long quantity;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getOfferId() {
        return this.offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
