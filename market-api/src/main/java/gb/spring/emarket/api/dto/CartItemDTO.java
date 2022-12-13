package gb.spring.emarket.api.dto;


public class CartItemDTO {
    private Long productId;
    private String title;
    private int quantity;
    private float costPerItem;
    private float shippingCost;
    private float totalCost;

    public CartItemDTO() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getCostPerItem() {
        return costPerItem;
    }

    public void setCostPerItem(float costPerItem) {
        this.costPerItem = costPerItem;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
}
