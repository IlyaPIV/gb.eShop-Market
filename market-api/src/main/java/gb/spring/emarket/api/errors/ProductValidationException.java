package gb.spring.emarket.api.errors;


import java.util.List;


public class ProductValidationException extends RuntimeException {
    private List<String> errorMessages;

    public ProductValidationException(List<String> errorFieldsMessages) {
        super(String.join(", ", errorFieldsMessages));
        this.errorMessages = errorFieldsMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
