package gb.spring.emarket.api.errors;


import java.util.List;


public class ValidationException extends RuntimeException {
    private List<String> errorMessages;

    public ValidationException(List<String> errorFieldsMessages) {
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
