package gb.spring.emarket.core.controllers;

import gb.spring.emarket.api.errors.ErrorMessage;
import gb.spring.emarket.core.dto.AuthRequestDTO;
import gb.spring.emarket.core.security.JwtTokenUtil;
import gb.spring.emarket.core.dto.AuthResponseDTO;
import gb.spring.emarket.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/v2/auth")
public class AuthenticationController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequestDTO authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new ErrorMessage("Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateNewToken(userDetails);
        return ResponseEntity.ok(new AuthResponseDTO(token, userDetails.getAuthorities()));
    }
}
