package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;

public class NonClientOrderCreationRequest extends Exception {
    
    public NonClientOrderCreationRequest(User user) {
        super("The user with the id" + user.getId() + " is not a client and thus cannot make an order");
    }
}
