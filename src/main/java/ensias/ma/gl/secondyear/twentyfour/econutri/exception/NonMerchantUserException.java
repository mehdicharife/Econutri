package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;

public class NonMerchantUserException extends Exception {
    
    public NonMerchantUserException(User user) {
        super("The user with the identifier " + user.getId() + " and email " + user.getEmail() + 
        " is not a merchant.");
    }
}
