package gb.spring.emarket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Data
public class AuthResponseDTO {
    private String token;
    private List<String> authorities;

    public AuthResponseDTO(String token) {
        this.token = token;
    }

    public AuthResponseDTO(String token,
                           Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.authorities = authorities.stream().map(Object::toString).toList();
    }
}
