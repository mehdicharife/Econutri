package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

public class OfferIdNotFoundException extends Exception {
    
    public OfferIdNotFoundException(Long id) {
        super("There is no offer with the identifier " + id);
    }
}
