package gb.spring.emarket.orders.services;

import gb.spring.emarket.api.grpc.Buyer;
import gb.spring.emarket.api.grpc.CleanCartServiceGrpc;
import gb.spring.emarket.api.grpc.CleanResult;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class CleanCartGrpcClientService {

    @GrpcClient("grpc-cart-service")
    CleanCartServiceGrpc.CleanCartServiceBlockingStub synchronousClient;

    @GrpcClient("grpc-cart-service")
    CleanCartServiceGrpc.CleanCartServiceStub asynchronousClient;

    public boolean sentCleanCartCommand(String name) {
        System.out.println("Sending command to clean " + name + "'s shopping cart...");
        Buyer buyer = Buyer.newBuilder().setName(name).build();

        CleanResult cleanResult = synchronousClient.cleanByBuyer(buyer);
        System.out.println("Result: " + cleanResult.getResult());

        return cleanResult.getResult();
    }
}
