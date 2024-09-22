package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductNameAlreadyExistsException extends Exception{
    public ProductNameAlreadyExistsException(String productName) {
        super("There is already a product with the name " + productName);
    }    
}
