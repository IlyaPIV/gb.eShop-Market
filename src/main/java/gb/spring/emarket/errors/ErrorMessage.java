package gb.spring.emarket.errors;

import java.util.Date;

public class ErrorMessage {
    private final String message;
    private final Date date;

    public ErrorMessage(String message) {
        this.message = message;
        this.date = new Date();
    }
}
