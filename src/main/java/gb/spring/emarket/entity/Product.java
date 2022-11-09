package gb.spring.emarket.entity;

import gb.spring.emarket.dto.ProductDTO;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private float cost;

    public Product(String title, float cost) {
        this.title = title;
        this.cost = cost;
    }

    public Product(ProductDTO productDTO){
        this.id = productDTO.getId();
        this.cost = productDTO.getCost();
        this.title = productDTO.getTitle();
    }
}
