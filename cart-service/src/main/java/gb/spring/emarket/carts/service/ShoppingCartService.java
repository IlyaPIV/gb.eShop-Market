package gb.spring.emarket.carts.service;

import gb.spring.emarket.api.dto.CartItemDTO;
import gb.spring.emarket.api.dto.ProductDTO;
import gb.spring.emarket.api.dto.ShoppingCartDTO;
import gb.spring.emarket.api.errors.ProductNotFoundException;
import gb.spring.emarket.api.errors.ProductValidationException;
import gb.spring.emarket.carts.entity.CartItem;
import gb.spring.emarket.carts.integrations.ProductServiceIntegration;
import gb.spring.emarket.carts.mapper.CartItemMapper;
import gb.spring.emarket.carts.rabbitmq.RabbitmqMessageSender;
import gb.spring.emarket.carts.repository.BasicShoppingCartRepository;
import gb.spring.emarket.api.errors.ShoppingCartWebServiceError;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Data
@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartService {

    public static final float SHIPPING_COST_PER_ITEM = 0.5f;

    private final BasicShoppingCartRepository shoppingCartRepo;
    private final ProductServiceIntegration productService;
    private final RabbitmqMessageSender rabbitmqMessageSender;

    public void addProduct(ProductDTO dto) {

        if (dto.getCount() == 0)
            throw new ProductValidationException(Collections.singletonList("Items count should be greater than 0!"));

        Long productId = dto.getId();
        validateProductId(productId);
        CartItem item = CartItemMapper.fromProductDTO(dto);

        if (shoppingCartRepo.isPresentInCart(item)) {
            shoppingCartRepo.changeCount(item, dto.getCount() + shoppingCartRepo.getItemQuantity(item));
            rabbitmqMessageSender.sendToLogger("Product " + dto.getTitle() + " was updated in shopping cart. Total count = " + dto.getCount());
        } else {
            shoppingCartRepo.addProduct(item, dto.getCount());
            rabbitmqMessageSender.sendToLogger("Product " + dto.getTitle() + " was added to shopping cart. Total count = " + dto.getCount());
        }
    }

    public void removeProduct(CartItemDTO dto) {
        CartItem item = CartItemMapper.fromCartItemDTO(dto);
        if (shoppingCartRepo.isPresentInCart(item)) {
            shoppingCartRepo.deleteProduct(item);
        } else {
            throw new ProductNotFoundException("Product with ID=" + dto.getProductId() + " is not present in Shopping Cart");
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
        if (errors.size() > 0) throw new ProductValidationException(errors);

        shoppingCartRepo.changeCount(item, newCount);
        rabbitmqMessageSender.sendToLogger("Product " + dto.getTitle() + " was updated in shopping cart. Total count = " + newCount);
    }

    public void validateProductId(Long id) {
        if (id == null) throw new ProductValidationException(List.of("Product's ID = NULL."));

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
