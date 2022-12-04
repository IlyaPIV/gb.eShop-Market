package gb.spring.emarket.controllers;

import gb.spring.emarket.dto.CartItemDTO;
import gb.spring.emarket.dto.ShoppingCartDTO;
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

    @GetMapping("/prods")
    public List<CartItemDTO> getAllInCart() {
        return service.getAll();
    }

    @GetMapping("/totalCount")
    public Integer getTotalCount() {
        return service.getTotalCount();
    }

    @GetMapping("/totalCost")
    public Float getTotalCost() {
        return service.getTotalCost();
    }

    @GetMapping
    public ResponseEntity<ShoppingCartDTO> getShoppingCart() {
        return new ResponseEntity<>(service.getShoppingCart(), HttpStatus.OK);
    }

    @PostMapping()
    public void addToCart(ProductDTO dto) {
        service.addProduct(dto);
    }

    @PutMapping()
    public void changeCount(CartItemDTO dto) {
        service.changeCount(dto);
//        return new ResponseEntity<>("Product's count in cart has been changed", HttpStatus.OK);
    }

    @DeleteMapping("/")
    public void removeFromCart(@RequestParam(name = "item") CartItemDTO item) {
        service.removeProduct(item);
//        return new ResponseEntity<>("Product with ID=" + id + " has been removed", HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public void removeAll() {
        service.removeAll();
    }
}