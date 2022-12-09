package gb.spring.emarket.core.errors;

import lombok.Data;

import java.util.Date;


@Data
public class ErrorMessage {
    private final String message;
    private final Date date;
    private final int httpErrorStatus;

    public ErrorMessage(String message, int httpErrorStatus) {
        this.message = message;
        this.httpErrorStatus = httpErrorStatus;
        this.date = new Date();
    }

    public ErrorMessage(String message) {
        this.message = message;
        this.httpErrorStatus = 400;
        this.date = new Date();
    }

}
