package gb.spring.emarket.services;

import gb.spring.emarket.dto.UserDTO;
import gb.spring.emarket.entity.Role;
import gb.spring.emarket.entity.User;
import gb.spring.emarket.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final int USERS_PER_PAGE = 10;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }


    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public Page<UserDTO> getUsersPage(int pageNum) {
        if (pageNum < 1) pageNum = 1;

        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);
        Page<User> userPage = userRepository.findAll(pageable);

        return convertPageToDTO(userPage);
    }

    private UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getUsername());
        dto.setCreated(user.getCreatedAt());
        dto.setEmail(user.getEmail());
        List<Role> roleList = user.getRoles();
        StringBuilder sb = new StringBuilder();
        roleList.forEach(role -> sb.append("[").append(role.getName()).append("],"));
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
        dto.setRoles(sb.toString());
        return dto;
    }

    private Page<UserDTO> convertPageToDTO(Page<User> userPage) {
        return userPage.map(this::fromUser);
    }
}
