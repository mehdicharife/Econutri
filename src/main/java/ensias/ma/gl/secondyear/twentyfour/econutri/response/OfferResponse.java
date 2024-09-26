package ensias.ma.gl.secondyear.twentyfour.econutri.response;

import java.util.Date;


public class OfferResponse {

    private Long id;

    private Long productId;

    private Long publisherId;

    private Date expirationDate;

    private Long availableQuantity;

    private String description;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPublisherId() {
        return this.publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }


    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getAvailableQuantity() {
        return this.availableQuantity;
    }

    public void setAvailableQuantity(Long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
