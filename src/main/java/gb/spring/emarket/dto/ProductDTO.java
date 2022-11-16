package gb.spring.emarket.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private float cost;
    private int count = 0;

    public ProductDTO() {
    }

    public ProductDTO(String title, float cost) {
        this.title = title;
        this.cost = cost;
    }

    public ProductDTO(Long id, String title, float cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

}
