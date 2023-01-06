package gb.spring.emarket.api.errors;

public class ProductsWebServiceError extends WebServiceErrorMessage {
    public enum ProductsServiceErrors {
        SERVICE_ERROR,
        PRODUCT_NOT_FOUND,
        VALIDATION_ERROR
    }

    public ProductsWebServiceError(String message, String errorCode) {
        super(message, errorCode);
    }
}
