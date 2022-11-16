package gb.spring.emarket.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private float cost;

    public Product(Long id, String title, float cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

}
