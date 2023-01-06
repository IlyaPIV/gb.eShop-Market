package gb.spring.emarket.core.services;

import gb.spring.emarket.api.dto.ProductDTO;
import gb.spring.emarket.api.errors.ProductValidationException;
import gb.spring.emarket.core.entity.Product;
import gb.spring.emarket.api.errors.ProductNotFoundException;
import gb.spring.emarket.core.mappers.ProductMapper;
import gb.spring.emarket.core.repositories.ProductRepository;
import gb.spring.emarket.core.specifications.ProductSpecification;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final int PRODUCTS_PER_PAGE = 8;


    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> {
            throw new ProductNotFoundException("There is no product with ID=" + id + " in database");
        });
        log.debug("Product with ID=" + id + " has been founded and returned in response.");
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
    public void update(ProductDTO productDTO) {
        Long prodId = productDTO.getId();
        if (prodId != null) {
            Product dbProduct = repository.findById(prodId)
                    .orElseThrow(() -> {
                        throw new ProductNotFoundException("No Product with ID = " + prodId + " in database");
                    });
            dbProduct.setTitle(productDTO.getTitle());
            dbProduct.setCost(productDTO.getCost());
            repository.save(dbProduct);
            log.info("Product with ID= " + prodId + " has been updated.");
        } else {
            log.error("UPDATE PRODUCT REQUEST: product ID = null");
            throw new ProductValidationException(List.of("UPDATE PRODUCT REQUEST: product ID = null"));
        }
    }

    public void delete(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> {
            throw new ProductNotFoundException("No Product with ID = " + id + " in database");
        });
        repository.delete(product);
        log.info("Product with ID= " + id + " was deleted");
    }

    public ProductDTO addNew(ProductDTO incomingProduct) {
        Product product = ProductMapper.MAPPER.toProduct(incomingProduct);
        log.info("New product has been added to DB.");
        return ProductMapper.MAPPER.fromProduct(repository.save(product));
    }

    public Optional<Product> getByID(Long id) {
        return repository.findById(id);
    }

    public List<Product> getAll() {
        return (List<Product>) repository.findAll();
    }
}
