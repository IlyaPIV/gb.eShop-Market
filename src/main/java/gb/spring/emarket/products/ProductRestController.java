package gb.spring.emarket.products;

import gb.spring.emarket.dto.ProductDTO;
import gb.spring.emarket.errors.ErrorMessage;
import gb.spring.emarket.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v2/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService service;
    private final ProductValidator validator;

    @GetMapping()
    public Page<ProductDTO> getAllProducts(@RequestParam(name = "page", defaultValue = "1") int pageNum,
                                           @RequestParam(name = "minPrice", required = false) Integer minPrice,
                                           @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
                                           @RequestParam(name = "name", required = false) String partName) {
        return service.getPage(pageNum, minPrice, maxPrice, partName);
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping()
    public ProductDTO addNewProduct(@RequestBody ProductDTO productDTO) {
        validator.validate(productDTO);
        return service.addNew(productDTO);
    }

    @PutMapping()
    public void updateProduct(@RequestBody ProductDTO productDTO) {
        validator.validate(productDTO);
        service.update(productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        service.delete(id);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleNotFoundException(NoSuchElementException ex) {
        ErrorMessage errorResponse = new ErrorMessage("Couldn't find in DataBase any Product with this id",
                HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
