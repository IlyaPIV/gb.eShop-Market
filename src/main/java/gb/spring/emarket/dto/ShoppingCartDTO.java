package gb.spring.emarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {
    private int totalItems;
    private float totalCost;

    private List<ProductDTO> products;

    public ShoppingCartDTO(float totalCost) {
        this.totalCost = totalCost;
    }

    public ShoppingCartDTO(int totalItems) {
        this.totalItems = totalItems;
    }

}
