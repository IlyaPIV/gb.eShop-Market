package gb.spring.emarket.api.dto;


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
