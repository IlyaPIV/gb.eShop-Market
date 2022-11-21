package gb.spring.emarket.controllers;

import gb.spring.emarket.dto.UserDTO;
import gb.spring.emarket.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    @GetMapping()
    public Page<UserDTO> getAllUsers(@RequestParam(name = "page", defaultValue = "1") int pageNum) {
        return userService.getUsersPage(pageNum);
    }
}
