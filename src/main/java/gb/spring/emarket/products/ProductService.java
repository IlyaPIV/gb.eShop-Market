package gb.spring.emarket.products;

import gb.spring.emarket.dto.ProductDTO;
import gb.spring.emarket.entity.Product;
import gb.spring.emarket.errors.ProductNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Data
public class ProductService {

    @Autowired
    private ProductRepository repository;

    private final int PRODUCTS_PER_PAGE = 8;


    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    public ProductDTO findById(Long id) throws NoSuchElementException {
        return new ProductDTO(repository.findById(id).orElseThrow());
    }

    public Page<ProductDTO> getPage(int pageNum) {
        if (pageNum < 1) pageNum = 1;

        Page<Product> products = repository.findAll(PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE));
        return products.map(ProductDTO::new);
    }

    public ProductDTO save(ProductDTO productDTO) throws NoSuchElementException {
        Product product = null;
        Long prodId = productDTO.getId();
        if (prodId != null) {
            product = repository.findById(prodId)
                    .orElseThrow();
            product.setTitle(productDTO.getTitle());
            product.setCost(productDTO.getCost());
        } else {
            product = repository.save(new Product(productDTO));
        }

        return new ProductDTO(product);
    }


    public void delete(Long id) throws NoSuchElementException {
        Product product = repository.findById(id).orElseThrow();
        repository.delete(product);
    }

    public ProductDTO addNew(ProductDTO incomingProduct) {
        return new ProductDTO(repository.save(new Product(incomingProduct)));
    }

    public List<Product> findAllWithFilter(Float min, Float max) {
        if (min == 0 && max == 0) return (List<Product>) repository.findAll();

        if (min < max) {
            return repository.findAllBetweenMinMaxPrice(min, max);
        } else {
            return repository.findAllWithPriceHigherThan(min);
        }
    }


}
