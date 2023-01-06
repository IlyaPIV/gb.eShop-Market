package gb.spring.emarket.api.errors;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Web service error model")
public class WebServiceErrorMessage {
    @Schema(description = "error's description")
    private final String message;
    @Schema(description = "date:time of error")
    private final Date date;
    @Schema(description = "internal web-service's code of error")
    private final String errorCode;

    public WebServiceErrorMessage(String message, String errorCode) {
        this.message = message;
        this.date = new Date();
        this.errorCode = errorCode;
    }

    public WebServiceErrorMessage(String message) {
        this.message = message;
        this.date = new Date();
        this.errorCode = "unknown";
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
