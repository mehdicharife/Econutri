package ensias.ma.gl.secondyear.twentyfour.econutri.request;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;


public class OfferCreationRequest {
    
    private Long productId;

    private String productName;

    private MultipartFile productImage;

    private Double unitPrice;

    private Long availableQuantity;

    private Date expirationDate;

    private String description;


    
    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public MultipartFile getProductImage() {
        return this.productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }


    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getAvailableQuantity() {
        return this.availableQuantity;
    }

    public void setAvailableQuantity(Long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
