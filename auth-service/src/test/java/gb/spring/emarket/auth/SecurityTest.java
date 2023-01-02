package gb.spring.emarket.auth;

import gb.spring.emarket.auth.model.Role;
import gb.spring.emarket.auth.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

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

    @MockBean
    private UserService userService;

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

    @Test
    public void createAuthTokenTest() throws Exception {
        String jsonRequest = "{\n" +
                "\t\"username\" \"login\",\n" +
                "\t\"password\": \"password\"\n" +
                "}";
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        UserDetails userDetails = new User("admin",
                "$2a$10$vkKCO4agyoCoEpZ1uqzAk.Q0I5OWF24ijMhjzYsceJ4Q0G0fhsca6",
                Collections.singleton(new SimpleGrantedAuthority(role.getName())));

        Mockito.doReturn(userDetails).when(userService).loadUserByUsername("login");
        mvc.perform(
                        post("/api/v1/login")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
