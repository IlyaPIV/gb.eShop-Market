package gb.spring.emarket.core.errors;

import gb.spring.emarket.api.errors.ProductNotFoundException;
import gb.spring.emarket.api.errors.ProductValidationException;
import gb.spring.emarket.api.errors.ProductsWebServiceError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ProductsWebServiceError> catchValidationException(ProductValidationException ex) {
        String errorLog = String.join(", ", ex.getErrorMessages());
        log.error("Validation exception: " + errorLog);
        return new ResponseEntity<>(new ProductsWebServiceError(errorLog,
                ProductsWebServiceError.ProductsServiceErrors.VALIDATION_ERROR.toString()),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ProductsWebServiceError> handleNotFoundException(ProductNotFoundException ex) {
        ProductsWebServiceError errorResponse = new ProductsWebServiceError(ex.getMessage(),
                ProductsWebServiceError.ProductsServiceErrors.PRODUCT_NOT_FOUND.toString());
        log.error("ProductNotFound exception: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
