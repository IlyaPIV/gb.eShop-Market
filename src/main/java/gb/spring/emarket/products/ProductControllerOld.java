package gb.spring.emarket.products;

import gb.spring.emarket.dto.ProductDTO;
import gb.spring.emarket.errors.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductControllerOld {
    @Autowired
    private ProductService service;

    @GetMapping("/")
    public Page<ProductDTO> getAllProducts(@RequestParam(name = "page", defaultValue = "1") int pageNum) {
        return service.getPage(pageNum);
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        try {
            return service.findById(id);
        } catch (NoSuchElementException ex) {
            throw new ProductNotFoundException("Couldn't find any Product with id = " + id);
        }
    }

//    @DeleteMapping("/delete/{id}")
//    public void deleteProductById(@PathVariable Long id){
//        service.delete(id);
//    }

    @GetMapping("/delete/{id}")
    public void wrongDeleteById(@PathVariable Long id) {
        try {
            service.delete(id);
        } catch (NoSuchElementException ex) {
            throw new ProductNotFoundException("Couldn't find any Product with id = " + id);
        }
    }

    @PostMapping("/")
    public ProductDTO addNewProduct(@RequestBody ProductDTO product) {
        return service.addNew(product);
    }

    @GetMapping("/filter")
    public List<ProductDTO> findMinMax(@RequestParam(name = "min", defaultValue = "0") Float min,
                                       @RequestParam(name = "max", defaultValue = "0") Float max) {
        return service.findAllWithFilter(min, max).stream().map(ProductDTO::new).collect(Collectors.toList());
    }
}
