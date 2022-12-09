package gb.spring.emarket.core.specifications;

import gb.spring.emarket.core.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> costGreaterThanOrEqualTo(Integer price) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), price));
    }

    public static Specification<Product> costLessThanOrEqualTo(Integer price) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"), price));
    }

    public static Specification<Product> titleLike(String namePart) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", namePart)));
    }

}
