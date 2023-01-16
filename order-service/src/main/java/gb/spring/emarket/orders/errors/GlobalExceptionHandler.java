package gb.spring.emarket.orders.errors;

import gb.spring.emarket.api.errors.WebServiceErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<WebServiceErrorMessage> catchCartServiceIntegrationException(CartServiceIntegrationError er) {
        log.error(er.getMessage(), er);
        return new ResponseEntity<>(new WebServiceErrorMessage(er.getMessage(), "CART_SERVICE_INTEGRATION_ERROR"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<WebServiceErrorMessage> catchOrderNotFoundException(OrderNotFoundException ex) {
        log.error("Order not found: " + ex.getMessage());
        return new ResponseEntity<>(new WebServiceErrorMessage(ex.getMessage(), "ORDER_NOT_FOUND"), HttpStatus.NOT_FOUND);
    }
}
