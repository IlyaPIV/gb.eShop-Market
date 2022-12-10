package gb.spring.emarket.core.mappers;

import gb.spring.emarket.api.dto.ProductDTO;
import gb.spring.emarket.core.entity.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.function.Function;

@Mapper
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDTO productDTO);

    @InheritInverseConfiguration
    ProductDTO fromProduct(Product product);

    default Page<ProductDTO> fromProductPage(Page<Product> productPage) {
        return productPage.map(new Function<Product, ProductDTO>() {
            @Override
            public ProductDTO apply(Product product) {
                return fromProduct(product);
            }
        });
    }

    ;
}
