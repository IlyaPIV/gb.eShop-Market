package gb.spring.emarket.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {

    private float shippingCostPerItem;
    private int totalItems;
    private float totalProductsCost;
    private List<CartItemDTO> products = new ArrayList<>();

}
