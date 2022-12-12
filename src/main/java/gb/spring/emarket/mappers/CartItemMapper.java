package gb.spring.emarket.mappers;

import gb.spring.emarket.dto.CartItemDTO;
import gb.spring.emarket.dto.ProductDTO;
import gb.spring.emarket.entity.CartItem;
import gb.spring.emarket.services.ShoppingCartService;

public class CartItemMapper {

    public static CartItem fromProductDTO(ProductDTO productDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productDTO.getId());
        cartItem.setTitle(productDTO.getTitle());
        cartItem.setCost(productDTO.getCost());

        return cartItem;
    }

    public static CartItemDTO toCartItemDTO(CartItem item, int quantity) {
        CartItemDTO dto = new CartItemDTO();
        dto.setTitle(item.getTitle());
        dto.setProductId(item.getProductId());
        dto.setCostPerItem(item.getCost());
        dto.setQuantity(quantity);
        dto.setShippingCost(quantity * ShoppingCartService.SHIPPING_COST_PER_ITEM);
        dto.setTotalCost(quantity * (item.getCost() + ShoppingCartService.SHIPPING_COST_PER_ITEM));

        return dto;
    }

    public static CartItem fromCartItemDTO(CartItemDTO dto) {
        CartItem item = new CartItem();
        item.setProductId(dto.getProductId());
        item.setTitle(dto.getTitle());
        item.setCost(dto.getCostPerItem());

        return item;
    }
}
