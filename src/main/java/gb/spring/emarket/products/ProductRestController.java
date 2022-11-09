package gb.spring.emarket.products;

import gb.spring.emarket.dto.ProductDTO;
import gb.spring.emarket.errors.ErrorMessage;
import gb.spring.emarket.errors.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v2/products")
public class ProductRestController {
    @Autowired
    private ProductService service;

    @GetMapping()
    public Page<ProductDTO> getAllProducts(@RequestParam(name = "page", defaultValue = "1") int pageNum) {
        return service.getPage(pageNum);
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping()
    public ProductDTO addNewProduct(@RequestBody ProductDTO product) {
        return service.addNew(product);
    }

    @PutMapping()
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        return service.save(productDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/filter")
    public List<ProductDTO> findMinMax(@RequestParam(name = "min", defaultValue = "0") Float min,
                                       @RequestParam(name = "max", defaultValue = "0") Float max) {
        return service.findAllWithFilter(min, max).stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleNotFoundException(ProductNotFoundException ex) {
        ErrorMessage errorResponse = new ErrorMessage("Couldn't find in DataBase any Product with this id",
                HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleNotFoundException(NoSuchElementException ex) {
        ErrorMessage errorResponse = new ErrorMessage("Couldn't find in DataBase any Product with this id",
                HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleNullPointerException(NullPointerException ex) {
        ErrorMessage errorResponse = new ErrorMessage(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
}
