package gb.spring.emarket.auth.dto;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;


public class AuthResponseDTO {
    private String token;
    private List<String> authorities;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token,
                           Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.authorities = authorities.stream().map(Object::toString).toList();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
