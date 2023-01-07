package gb.spring.emarket.carts.errors;

import gb.spring.emarket.api.errors.ProductNotFoundException;
import gb.spring.emarket.api.errors.ProductValidationException;
import gb.spring.emarket.api.errors.ShoppingCartWebServiceError;
import gb.spring.emarket.carts.integrations.ProductServiceIntegration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ShoppingCartWebServiceError> catchValidationException(ProductValidationException ex) {
        String errorLog = String.join(", ", ex.getErrorMessages());

        log.error("Validation exception: " + errorLog);
        ShoppingCartWebServiceError errorResponse = new ShoppingCartWebServiceError(errorLog,
                ShoppingCartWebServiceError.CartServiceErrors.PRODUCT_VALIDATION_FAIL.name());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ShoppingCartWebServiceError> handleProductNotFoundException(ProductNotFoundException ex) {
        log.error("Product not found exception: " + ex.getMessage());
        ShoppingCartWebServiceError errorResponse = new ShoppingCartWebServiceError(ex.getMessage(),
                ShoppingCartWebServiceError.CartServiceErrors.NO_SUCH_PROD_IN_CART.name());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ShoppingCartWebServiceError> handleProductIntegrationException(ProductServiceIntegrationException ex) {
        log.error("Integration exception: " + ex.getMessage());
        ShoppingCartWebServiceError errorResponse = new ShoppingCartWebServiceError(ex.getMessage(),
                ShoppingCartWebServiceError.CartServiceErrors.INTEGRATION_FAIL.name());
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
