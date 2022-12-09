package gb.spring.emarket.core.errors;

import lombok.Data;


@Data
public class ShoppingCardException extends RuntimeException {

    public ShoppingCardException(String message) {
        super(message);
    }
}
