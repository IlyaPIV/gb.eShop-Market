package gb.spring.emarket.api.dto;

public class StringResponseDTO {
    private String response;

    public StringResponseDTO() {
    }

    public StringResponseDTO(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
