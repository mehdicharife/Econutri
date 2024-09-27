package ensias.ma.gl.secondyear.twentyfour.econutri.exception;


public class UnsupportedRoleException extends Exception {
    
    public UnsupportedRoleException(String role) {
        super("The role " + role + " is not supported");
    }
}
