package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

public class ProductNameNotFoundException extends Exception {
    public ProductNameNotFoundException(String productName) {
        super("There is no product with the name " + productName);
    }
}
