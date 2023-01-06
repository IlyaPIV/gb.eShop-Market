package gb.spring.emarket.carts.errors;

public class ProductServiceIntegrationException extends RuntimeException {
    public ProductServiceIntegrationException(String message) {
        super(message);
    }
}
