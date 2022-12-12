package gb.spring.emarket.repositories;

import gb.spring.emarket.dto.CartItemDTO;
import gb.spring.emarket.entity.CartItem;
import gb.spring.emarket.mappers.CartItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class BasicShoppingCartRepository implements AbstractShoppingCartRepository {

    private final Map<CartItem, Integer> shoppingCart = new HashMap<>();

    private float totalCost = 0;
    private int totalCount = 0;


    public void addProduct(CartItem item, Integer quantity) {
        totalCost += item.getCost() * quantity;
        totalCount += quantity;

        shoppingCart.put(item, quantity);
    }

    public boolean isPresentInCart(CartItem item) {
        return shoppingCart.get(item) != null;
    }

    public void deleteProduct(CartItem item) {
        int count = shoppingCart.get(item);
        totalCount -= count;
        totalCost -= item.getCost() * count;

        shoppingCart.remove(item);
    }

    public void changeCount(CartItem item, Integer quantity) {
        int currentCount = shoppingCart.get(item);
        totalCount += (quantity - currentCount);
        totalCost += (quantity - currentCount) * item.getCost();
    }

    public void deleteAll() {
        totalCost = 0;
        totalCount = 0;
        shoppingCart.clear();
    }

    public Integer getItemQuantity(CartItem item) {
        return shoppingCart.get(item);
    }

    public float getTotalCost() {
        return totalCost;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<CartItemDTO> getAllCartItems() {
        List<CartItemDTO> itemDTOList = new ArrayList<>();
        for (Map.Entry<CartItem, Integer> item :
                shoppingCart.entrySet()) {
            CartItemDTO newDto = CartItemMapper.toCartItemDTO(item.getKey(), item.getValue());
            itemDTOList.add(newDto);
        }
        return itemDTOList;
    }


}
