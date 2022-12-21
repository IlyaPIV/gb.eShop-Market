package gb.spring.emarket.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void securityAccessAllowedTest() throws Exception {
        mvc.perform(get("/api/v2/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void securityAccessDeniedTest() throws Exception {
        mvc.perform(get("/api/v1/orders"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

//    @Test
//    @WithMockUser(username = "bob", roles = {"ADMIN", "MANAGER"})
//    public void securityCheckUserTest() throws Exception {
//        mvc.perform(get("/api/v1/orders"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

    @Test
    public void securityTokenTest() throws Exception {
        String jsonRequest = "{\n" +
                "\t\"username\" \"admin\",\n" +
                "\t\"password\": \"password\"\n" +
                "}";
        MvcResult result = mvc.perform(
                        post("/auth")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
    }
}
