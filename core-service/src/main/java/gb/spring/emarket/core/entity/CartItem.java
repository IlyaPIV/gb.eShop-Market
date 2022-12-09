package gb.spring.emarket.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    private Long productId;
    private String title;
    private float cost;
}
