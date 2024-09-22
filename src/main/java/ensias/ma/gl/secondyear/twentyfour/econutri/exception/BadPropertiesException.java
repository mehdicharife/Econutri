package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

import java.util.Map;

public class BadPropertiesException extends Exception{ 
    private Map<String, String> fieldsErrors;
    
    public BadPropertiesException(Map<String, String> fieldsErrors) {
        this.fieldsErrors = fieldsErrors;
    }

    public final Map<String, String> getFieldsErrors() {
        return this.fieldsErrors;
    }
}
