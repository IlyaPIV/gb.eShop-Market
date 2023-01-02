package gb.spring.emarket.carts.service;

import gb.spring.emarket.api.dto.CartItemDTO;
import gb.spring.emarket.api.dto.ProductDTO;
import gb.spring.emarket.api.dto.ShoppingCartDTO;
import gb.spring.emarket.api.errors.ProductNotFoundException;
import gb.spring.emarket.api.errors.ValidationException;
import gb.spring.emarket.carts.entity.CartItem;
import gb.spring.emarket.carts.integrations.ProductServiceIntegration;
import gb.spring.emarket.carts.mapper.CartItemMapper;
import gb.spring.emarket.carts.repository.BasicShoppingCartRepository;
import gb.spring.emarket.api.errors.ShoppingCardException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Data
@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    public static final float SHIPPING_COST_PER_ITEM = 0.5f;

    private final BasicShoppingCartRepository shoppingCartRepo;
    private final ProductServiceIntegration productService;

    public void addProduct(ProductDTO dto) {

        if (dto.getCount() == 0)
            throw new ValidationException(Collections.singletonList("Items count should be greater than 0!"));

        Long productId = dto.getId();
        validateProductId(productId);
        CartItem item = CartItemMapper.fromProductDTO(dto);

        if (shoppingCartRepo.isPresentInCart(item)) {
            shoppingCartRepo.changeCount(item, dto.getCount() + shoppingCartRepo.getItemQuantity(item));
        } else {
            shoppingCartRepo.addProduct(item, dto.getCount());
        }
    }

    public void removeProduct(CartItemDTO dto) {
        CartItem item = CartItemMapper.fromCartItemDTO(dto);
        if (shoppingCartRepo.isPresentInCart(item)) {
            shoppingCartRepo.deleteProduct(item);
        } else {
            throw new ShoppingCardException("Product with ID=" + dto.getProductId() + " is not present in Shopping Cart");
        }
    }

    public void changeCount(CartItemDTO dto) {

        CartItem item = CartItemMapper.fromCartItemDTO(dto);
        int newCount = dto.getQuantity();

        List<String> errors = new ArrayList<>();
        if (!shoppingCartRepo.isPresentInCart(item)) {
            errors.add("Product with ID=" + item.getProductId() + " is not present in Shopping Cart");
        }
        if (newCount < 1) {
            errors.add("New count couldn't be less that 1");
        }
        if (errors.size() > 0) throw new ValidationException(errors);

        shoppingCartRepo.changeCount(item, newCount);

    }

    public void validateProductId(Long id) {
        if (id == null) throw new NullPointerException("Product's ID = NULL.");

        productService.getByID(id);

    }

    private List<CartItemDTO> getItemsDTOList() {
        return shoppingCartRepo.getAllCartItems();
    }

    public List<CartItemDTO> getAll() {

        return getItemsDTOList();
    }

    public ShoppingCartDTO getShoppingCart() {

        List<CartItemDTO> productDTOList = getItemsDTOList();
        int totalCount = shoppingCartRepo.getTotalCount();
        float totalCost = shoppingCartRepo.getTotalCost();

        return new ShoppingCartDTO(SHIPPING_COST_PER_ITEM, totalCount, totalCost, productDTOList);
    }

    public Float getTotalCost() {
        return shoppingCartRepo.getTotalCost();
    }

    public Integer getTotalCount() {
        return shoppingCartRepo.getTotalCount();
    }

    public void removeAll() {
        shoppingCartRepo.deleteAll();
    }
}
