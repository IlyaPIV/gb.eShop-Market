package gb.spring.emarket.orders.errors;

public class CartServiceIntegrationError extends RuntimeException {
    public CartServiceIntegrationError(String message) {
        super(message);
    }
}
