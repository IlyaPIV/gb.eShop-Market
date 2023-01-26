package gb.spring.emarket.orders.integrations;

import gb.spring.emarket.api.dto.ShoppingCartDTO;
import gb.spring.emarket.orders.errors.CartServiceIntegrationError;

public class GrpcCartIntegrationService extends CleanCartGrpcClientService implements CartIntegrationAdapter {

    @Override
    public ShoppingCartDTO getShoppingCart(String userName) {
        throw new CartServiceIntegrationError("This service does not support this command");
    }

    @Override
    public void cleanShoppingCart(String userName) {
        sentCleanCartCommand(userName);
    }
}
