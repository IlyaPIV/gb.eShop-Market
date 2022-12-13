package gb.spring.emarket.core.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", length = 64, nullable = false)
    private String username;
    @Column(name = "password", length = 64, nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void addRole(Role newRole) {
        roles.add(newRole);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }
}
