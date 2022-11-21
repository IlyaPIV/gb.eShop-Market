package gb.spring.emarket.controllers;

import gb.spring.emarket.services.ShoppingCartService;
import gb.spring.emarket.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/cart")
@RequiredArgsConstructor
public class ShoppingCartRestController {

    private final ShoppingCartService service;

    @GetMapping()
    public List<ProductDTO> getAllInCart() {
        return service.getAll();
    }

    @PostMapping()
    public ResponseEntity<String> addToCart(ProductDTO dto) {
        service.addProduct(dto);
        return new ResponseEntity<>("Product has been added to cart", HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<String> changeCount(ProductDTO dto) {
        service.changeCount(dto);
        return new ResponseEntity<>("Product's count in cart has been changed", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long id) {
        service.removeProduct(id);
        return new ResponseEntity<>("Product with ID=" + id + " has been removed", HttpStatus.OK);
    }
}
