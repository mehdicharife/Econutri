package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

public class EmailNotFoundException extends SingleBadPropertyException {

    public EmailNotFoundException(String email) {
        super("email", "There is no record matching the email " + email);
    } 
    
}
