package gb.spring.emarket.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> catchValidationException(ValidationException ex) {
        String errorLog = String.join(", ", ex.getErrorMessages());
        log.error("Validation error exception: " + errorLog);
        return new ResponseEntity<>(new ErrorMessage(errorLog, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleNullPointerException(NullPointerException ex) {
        log.error("NullPointer exception: " + ex.getMessage());
        ErrorMessage errorResponse = new ErrorMessage(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
