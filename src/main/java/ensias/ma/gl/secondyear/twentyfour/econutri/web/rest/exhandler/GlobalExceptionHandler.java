package ensias.ma.gl.secondyear.twentyfour.econutri.web.rest.exhandler;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.BadPropertiesException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleBadArgumentException(MethodArgumentNotValidException exception) {

        Map<String, String> errorMessage = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));


        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorMessage); 
    }

    @ExceptionHandler(BadPropertiesException.class) 
    public ResponseEntity<Map<String, String>> handleBadPropertiesException(BadPropertiesException exception) {

        return ResponseEntity.badRequest().body(exception.getFieldsErrors());
    }

    
    
}
