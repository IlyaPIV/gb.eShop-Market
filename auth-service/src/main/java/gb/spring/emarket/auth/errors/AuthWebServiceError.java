package gb.spring.emarket.auth.errors;

import gb.spring.emarket.api.errors.WebServiceErrorMessage;

public class AuthWebServiceError extends WebServiceErrorMessage {
    public enum AuthServiceErrors {
        USER_NOT_FOUND,
        BAD_CREDENTIALS,

        OTHER
    }

    public AuthWebServiceError(String message, String errorCode) {
        super(message, errorCode);
    }
}
