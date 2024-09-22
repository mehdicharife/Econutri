package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

import java.util.Map;

public class SingleBadPropertyException extends BadPropertiesException {

    public SingleBadPropertyException(String propertyName, String badnessReason) {
        super(
            Map.of(propertyName, badnessReason)
        );
    }
    
}
