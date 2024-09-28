package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.OrderState;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;

public class UnauthorizedOrderTransitionException extends Exception {

    public UnauthorizedOrderTransitionException(User user, OrderState currentState, OrderState newState) {
        super("The user with the id " + user.getId() + " is not authorized to transition the order from " + 
             currentState.name() + " to " + newState.name()
        );
    }
    
}
