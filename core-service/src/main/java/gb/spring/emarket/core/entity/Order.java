package gb.spring.emarket.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    private float totalProducts;

    private float shippingCost;

    private float totalCost;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "delivery_days")
    private int deliveryDays;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItemPosition> orderDetails = new HashSet<>();


    public Order(User user) {
        this.user = user;
    }
}
