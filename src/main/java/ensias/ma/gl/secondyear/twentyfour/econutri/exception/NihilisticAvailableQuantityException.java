package ensias.ma.gl.secondyear.twentyfour.econutri.exception;



public class NihilisticAvailableQuantityException extends Exception {
    public NihilisticAvailableQuantityException() {
        super("Available product quantity can't be equal to zero.");
    }
}
