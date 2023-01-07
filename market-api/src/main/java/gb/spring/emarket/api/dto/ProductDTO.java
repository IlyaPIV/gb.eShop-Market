package gb.spring.emarket.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product's model")
public class ProductDTO {
    @Schema(description = "Product's Id", example = "1")
    private Long id;
    @Schema(description = "Product's name", required = true, minLength = 3, example = "Sample product")
    private String title;
    @Schema(description = "Product's cost per item", example = "9.99")
    private float cost;
    @Schema(description = "Products count in operation", example = "3")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
