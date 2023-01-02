package gb.spring.emarket.orders.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_item_position")
public class OrderItemPosition {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;

    private int quantity;

    private float unitPrice;

    private float shippingCost;

    private float productCost;

    private float totalCost;
}
