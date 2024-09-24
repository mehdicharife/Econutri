package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

public class EmailTakenException extends SingleBadPropertyException {

    public EmailTakenException(String email) {
        super("email", "The email is already taken");
    }
    
}
