package gb.spring.emarket.core.controllers;

import gb.spring.emarket.core.dto.UserDTO;
import gb.spring.emarket.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserRestController {

    private final UserService userService;

    @Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    @GetMapping()
    public Page<UserDTO> getAllUsers(@RequestParam(name = "page", defaultValue = "1") int pageNum) {
        return userService.getUsersPage(pageNum);
    }
}
