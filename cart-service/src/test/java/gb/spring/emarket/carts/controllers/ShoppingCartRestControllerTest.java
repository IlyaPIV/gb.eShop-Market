package gb.spring.emarket.carts.controllers;

import gb.spring.emarket.api.dto.ProductDTO;
import gb.spring.emarket.carts.entity.CartItem;
import gb.spring.emarket.carts.integrations.ProductServiceIntegration;
import gb.spring.emarket.carts.mapper.CartItemMapper;
import gb.spring.emarket.carts.repository.BasicShoppingCartRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShoppingCartRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @MockBean
    private BasicShoppingCartRepository mockRepo;


    @Test
    @Order(1)
    public void addToCartTest() throws Exception {
        ProductDTO productDTO = new ProductDTO(15L, "Test item", 17.5f);
        productDTO.setCount(2);
        Mockito.doReturn(productDTO).when(productServiceIntegration).getByID(15L);
        CartItem item = CartItemMapper.fromProductDTO(productDTO);

        Mockito.doNothing().when(mockRepo).addProduct(item, 2);

        mvc.perform(post("/api/v1/carts/"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getCurrentCartTest() throws Exception {
        mvc.perform(get("/api/v1/carts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products").isArray());

    }

}