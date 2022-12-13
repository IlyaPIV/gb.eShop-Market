package gb.spring.emarket.core.soap;

import gb.spring.emarket.core.entity.Product;
import gb.spring.emarket.core.services.ProductService;
import gb.spring.emarket.core.soap.products.GetAllProductsRequest;
import gb.spring.emarket.core.soap.products.GetAllProductsResponse;
import gb.spring.emarket.core.soap.products.GetProductByIDRequest;
import gb.spring.emarket.core.soap.products.GetProductByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


// GET http://localhost:9090/eshop/ws/products.wsdl
@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.emarket.com/gb/ws/products";
    private final ProductService productService;

//    POST http://localhost:9090/eshop/ws
//
//    Content-Type: text/xml; charset=utf-8
//
//  <x:Envelope xmlns:x="http://schemas.xmlsoap.org/soap/envelope/" xmlns:prod="http://www.emarket.com/gb/ws/products">
//      <x:Header/>
//      <x:Body>
//          <prod:getProductByIDRequest>
//              <prod:id>14</prod:id>
//          </prod:getProductByIDRequest>
//      </x:Body>
//  </x:Envelope>
//
//  RESPONSE:
//
//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
//    <SOAP-ENV:Header/>
//    <SOAP-ENV:Body>
//        <ns2:getProductByIdResponse xmlns:ns2="http://www.emarket.com/gb/ws/products">
//            <ns2:product>
//                <ns2:id>14</ns2:id>
//                <ns2:title>Toilet paper</ns2:title>
//                <ns2:cost>3.03</ns2:cost>
//            </ns2:product>
//        </ns2:getProductByIdResponse>
//    </SOAP-ENV:Body>
//</SOAP-ENV:Envelope>

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIDRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIDRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        gb.spring.emarket.core.soap.products.Product productSOAP = new gb.spring.emarket.core.soap.products.Product();
        Product productDB = productService.getByID(request.getId()).orElseThrow();
        productSOAP.setId(productDB.getId());
        productSOAP.setTitle(productDB.getTitle());
        productSOAP.setCost(productDB.getCost());

        response.setProduct(productSOAP);
        return response;
    }


//    POST http://localhost:9090/eshop/ws
//
//    Content-Type: text/xml; charset=utf-8
//
//    <x:Envelope xmlns:x="http://schemas.xmlsoap.org/soap/envelope/" xmlns:prod="http://www.emarket.com/gb/ws/products">
//        <x:Header/>
//        <x:Body>
//            <prod:getAllProductsRequest>
//            </prod:getAllProductsRequest>
//        </x:Body>
//    </x:Envelope>

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.getAll().forEach(prod -> {
            gb.spring.emarket.core.soap.products.Product productSOAP = new gb.spring.emarket.core.soap.products.Product();
            productSOAP.setId(prod.getId());
            productSOAP.setTitle(prod.getTitle());
            productSOAP.setCost(prod.getCost());
            response.getProducts().add(productSOAP);
        });
        return response;
    }
}
