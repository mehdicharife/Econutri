package ensias.ma.gl.secondyear.twentyfour.econutri.request;


public class OrderCreationRequest {
    
    private Long offerId;

    private Long productQuantity;


    public Long getOfferId() {
        return this.offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getProductQuantity() {
        return this.productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

}
