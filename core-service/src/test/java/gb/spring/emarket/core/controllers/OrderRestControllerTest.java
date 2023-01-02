//package gb.spring.emarket.core.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import gb.spring.emarket.api.dto.CheckoutDTO;
//import gb.spring.emarket.core.services.OrderService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class OrderRestControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private OrderService service;
//
//    @Test
//    public void hasAuthorityTest() throws Exception {
//
//        CheckoutDTO checkoutDTO = new CheckoutDTO();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
//        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(checkoutDTO);
//
//        Mockito.doNothing().when(service).createOrder("username", checkoutDTO);
//
//        mvc.perform(
//                        post("/api/v2/orders")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .header("username", "User")
//                                .content(requestJson)
//                )
//                .andDo(print())
//                .andExpect(status().isCreated());
//    }
//}