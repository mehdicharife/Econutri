package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

import java.util.Map;

public class UserCreationException extends BadPropertiesException {
    
    public UserCreationException(Map<String, String> errors) {
        super(errors);
    }
}
