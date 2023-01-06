package gb.spring.emarket.carts.service;

import gb.spring.emarket.api.grpc.Buyer;
import gb.spring.emarket.api.grpc.CleanCartServiceGrpc;
import gb.spring.emarket.api.grpc.CleanResult;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CleanCartGrpcServerService extends CleanCartServiceGrpc.CleanCartServiceImplBase {
    @Override
    public void cleanByBuyer(Buyer request, StreamObserver<CleanResult> responseObserver) {

        System.out.println("Request received from \n" + request.getName());

        CleanResult cleanResult = CleanResult.newBuilder().setResult(true).build();


        responseObserver.onNext(cleanResult);
        responseObserver.onCompleted();
    }
}
