package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

public class UsedProductNameException extends Exception {

    public UsedProductNameException(String productName) {
        super("The product name " + productName + " is already used.");
    }
}
