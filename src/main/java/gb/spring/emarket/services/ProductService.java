package gb.spring.emarket.services;

import gb.spring.emarket.dto.ProductDTO;
import gb.spring.emarket.entity.Product;
import gb.spring.emarket.errors.ProductNotFoundException;
import gb.spring.emarket.mappers.ProductMapper;
import gb.spring.emarket.repositories.ProductRepository;
import gb.spring.emarket.specifications.ProductSpecification;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final int PRODUCTS_PER_PAGE = 8;


    public ProductDTO findById(Long id) throws NoSuchElementException {
        try {
            Product product = repository.findById(id).get();
            log.debug("Product with ID=" + id + " has been founded and returned in response.");
            return ProductMapper.MAPPER.fromProduct(product);
        } catch (NoSuchElementException ex) {
            throw new ProductNotFoundException("There is no product with ID=" + id + " in database");
        }
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
            log.info("Product with ID= " + prodId + " has been updated.");
        } else {
            log.error("UPDATE PRODUCT REQUEST: product ID = null");
            throw new NullPointerException("This operation is not supported: product ID = null");
        }
    }

    public void delete(Long id) throws NoSuchElementException {
        Product product = repository.findById(id).orElseThrow();
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

}
