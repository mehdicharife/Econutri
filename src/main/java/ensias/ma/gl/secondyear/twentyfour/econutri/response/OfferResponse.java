package ensias.ma.gl.secondyear.twentyfour.econutri.response;

import java.util.Date;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Product;


public class OfferResponse {

    private Long id;

    private Product product;

    private Double unitPrice;

    private Date expirationDate;

    private Long availableQuantity;

    private String description;

    private Long publisherId;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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
