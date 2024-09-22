package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

public class ProductIdNotFoundException extends SingleBadPropertyException{

    public ProductIdNotFoundException(Long productId) {
        super("productId", "The number " + productId + " does not correspnd to any existing product identifier");
    }
    
}
