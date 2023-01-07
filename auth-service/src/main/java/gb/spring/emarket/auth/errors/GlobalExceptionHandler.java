package gb.spring.emarket.auth.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<AuthWebServiceError> handleNullPointerException(NullPointerException ex) {
        log.error("NullPointer exception: " + ex.getMessage());

        AuthWebServiceError errorResponse = new AuthWebServiceError(ex.getMessage(), AuthWebServiceError.AuthServiceErrors.OTHER.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AuthWebServiceError> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("UsernameNotFound exception: " + ex.getMessage());

        AuthWebServiceError errorResponse = new AuthWebServiceError(ex.getMessage(), AuthWebServiceError.AuthServiceErrors.USER_NOT_FOUND.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
