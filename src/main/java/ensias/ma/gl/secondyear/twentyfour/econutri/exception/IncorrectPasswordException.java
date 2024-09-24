package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

public class IncorrectPasswordException extends SingleBadPropertyException {

    public IncorrectPasswordException(String password) {
        super("password", "Incorrect password");
    }
    
}
