package gb.spring.emarket.orders.integrations;

import gb.spring.emarket.api.dto.ShoppingCartDTO;

public interface CartIntegrationAdapter {

    public ShoppingCartDTO getShoppingCart(String userName);

    public void cleanShoppingCart(String userName);
}
