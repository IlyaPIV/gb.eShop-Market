package gb.spring.emarket.products.controllers;

import gb.spring.emarket.products.Product;
import gb.spring.emarket.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/rest")
public class ProductRestController {
    @Autowired
    private ProductService service;

    @GetMapping("/refresh")
    public void refreshList(){
        service.refreshList();
    }

    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return service.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Integer id){
        service.delete(id);
    }

    @PostMapping("/")
    public Product addNewProduct(@RequestParam(name = "name") String name,
                                 @RequestParam(name = "cost") Integer cost) {
        System.out.println(">>> incoming post new request");
        return service.addNew(name, cost);
    }
}
