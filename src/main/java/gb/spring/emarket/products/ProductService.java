package gb.spring.emarket.products;

import gb.spring.emarket.dto.ProductDTO;
import gb.spring.emarket.entity.Product;
import gb.spring.emarket.mappers.ProductMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final int PRODUCTS_PER_PAGE = 8;


    public ProductDTO findById(Long id) throws NoSuchElementException {
        Product product = repository.findById(id).orElseThrow();
        return ProductMapper.MAPPER.fromProduct(product);
    }

    public Page<ProductDTO> getPage(int pageNum, Integer minPrice, Integer maxPrice, String partName) {
        if (pageNum < 1) pageNum = 1;

        Specification<Product> productSpecification = Specification.where(null);
        if (minPrice != null)
            productSpecification = productSpecification.and(ProductSpecification.costGreaterThanOrEqualTo(minPrice));
        if (maxPrice != null)
            productSpecification = productSpecification.and(ProductSpecification.costLessThanOrEqualTo(maxPrice));
        if (partName != null) productSpecification = productSpecification.and(ProductSpecification.titleLike(partName));

        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
        Page<Product> products = repository.findAll(productSpecification, pageable);
        return ProductMapper.MAPPER.fromProductPage(products);
    }

    @Transactional
    public void update(ProductDTO productDTO) throws NoSuchElementException, NullPointerException {
        Long prodId = productDTO.getId();
        if (prodId != null) {
            Product dbProduct = repository.findById(prodId)
                    .orElseThrow();
            dbProduct.setTitle(productDTO.getTitle());
            dbProduct.setCost(productDTO.getCost());
            repository.save(dbProduct);
        } else {
            throw new NullPointerException("This operation is not supported: product ID = null");
        }
    }

    public void delete(Long id) throws NoSuchElementException {
        Product product = repository.findById(id).orElseThrow();
        repository.delete(product);
    }

    public ProductDTO addNew(ProductDTO incomingProduct) {
        Product product = ProductMapper.MAPPER.toProduct(incomingProduct);
        return ProductMapper.MAPPER.fromProduct(repository.save(product));
    }

}
