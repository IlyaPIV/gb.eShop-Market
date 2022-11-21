package gb.spring.emarket.errors;

import lombok.Data;

import java.util.List;

@Data
public class ValidationException extends RuntimeException {
    private List<String> errorMessages;

    public ValidationException(List<String> errorFieldsMessages) {
        super(String.join(", ", errorFieldsMessages));
        this.errorMessages = errorFieldsMessages;
    }
}
