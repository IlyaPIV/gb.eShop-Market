package gb.spring.emarket.errors;

import lombok.Data;


@Data
public class ShoppingCardException extends RuntimeException {

    public ShoppingCardException(String message) {
        super(message);
    }
}
