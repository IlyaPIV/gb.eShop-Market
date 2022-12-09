package gb.spring.emarket.core.mappers;

import gb.spring.emarket.core.dto.ProductDTO;
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

//    List<Product> toProductList(List<ProductDTO> productDTOList);
//
//    List<ProductDTO> fromProductList(List<Product> productList);
//
//    Page<Product> toProductPage(Page<ProductDTO> productDTOPage);

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
