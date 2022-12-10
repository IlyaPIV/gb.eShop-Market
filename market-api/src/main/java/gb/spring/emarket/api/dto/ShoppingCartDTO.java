package gb.spring.emarket.api.dto;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCartDTO {

    private float shippingCostPerItem;
    private int totalItems;
    private float totalProductsCost;
    private List<CartItemDTO> products = new ArrayList<>();

    public ShoppingCartDTO() {
    }

    public ShoppingCartDTO(float shippingCostPerItem, int totalItems, float totalProductsCost, List<CartItemDTO> products) {
        this.shippingCostPerItem = shippingCostPerItem;
        this.totalItems = totalItems;
        this.totalProductsCost = totalProductsCost;
        this.products = products;
    }

    public float getShippingCostPerItem() {
        return shippingCostPerItem;
    }

    public void setShippingCostPerItem(float shippingCostPerItem) {
        this.shippingCostPerItem = shippingCostPerItem;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public float getTotalProductsCost() {
        return totalProductsCost;
    }

    public void setTotalProductsCost(float totalProductsCost) {
        this.totalProductsCost = totalProductsCost;
    }

    public List<CartItemDTO> getProducts() {
        return products;
    }

    public void setProducts(List<CartItemDTO> products) {
        this.products = products;
    }
}
