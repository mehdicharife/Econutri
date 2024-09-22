package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PassedExpirationDateException extends Exception{
    
    public PassedExpirationDateException(Date expirationDate) {
        super("The expiration date " + expirationDate + " has already passed.");
    }
}
