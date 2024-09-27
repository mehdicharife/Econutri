package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

import java.util.Map;

public class OrderCreationException extends BadPropertiesException {
    
    public OrderCreationException(Map<String, String> errorsMap) {
        super(errorsMap);
    }

}
