package gb.spring.emarket.products.controllers;

import gb.spring.emarket.entity.Product;
import gb.spring.emarket.products.ProductNotFoundException;
import gb.spring.emarket.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestController {
    @Autowired
    private ProductService service;

    @GetMapping("/")
    public List<Product> getAllProducts(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id){
        try {
            return service.findById(id);
        } catch (ProductNotFoundException ex){
            return null;
        }
    }

//    @DeleteMapping("/delete/{id}")
//    public void deleteProductById(@PathVariable Long id){
//        service.delete(id);
//    }

    @GetMapping("/delete/{id}")
    public void wrongDeleteById(@PathVariable Long id){
        service.delete(id);
    }

    @PostMapping("/")
    public Product addNewProduct(@RequestBody Product product) {

        return service.addNew(product);
    }

    @GetMapping("/filter")
    public List<Product> findMinMax(@RequestParam(name = "min", defaultValue = "0") Float min,
                                    @RequestParam(name = "max", defaultValue = "0") Float max){
        return service.findAllWithFilter(min, max);
    }
}
