package ensias.ma.gl.secondyear.twentyfour.econutri.exception;


public class NegativePriceException extends SingleBadPropertyException {

    public NegativePriceException(Double price) {
        super("unitPrice", "The unit price cannot be negative and thus cannot have a value of " + price);
    }
    
}
