package gb.spring.emarket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDTO {
    private Long productId;
    private String title;
    private int quantity;
    private float costPerItem;
    private float shippingCost;
    private float totalCost;
}
