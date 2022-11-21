package gb.spring.emarket.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String roles;
    private LocalDateTime created;
}
