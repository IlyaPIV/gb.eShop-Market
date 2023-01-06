package gb.spring.emarket.api.errors;


public class ShoppingCartWebServiceError extends WebServiceErrorMessage {

    public enum CartServiceErrors {
        CART_IS_BROKEN,
        CART_NOT_FOUND,
        NO_SUCH_PROD_IN_CART,
        PRODUCT_VALIDATION_FAIL,
        INTEGRATION_FAIL

    }

    public ShoppingCartWebServiceError(String message, String errorCode) {
        super(message, errorCode);
    }
}
