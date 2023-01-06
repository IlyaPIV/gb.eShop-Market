package gb.spring.emarket.auth.controller;

import gb.spring.emarket.auth.dto.AuthRequestDTO;
import gb.spring.emarket.auth.dto.AuthResponseDTO;
import gb.spring.emarket.auth.errors.AuthWebServiceError;
import gb.spring.emarket.auth.security.JwtTokenUtil;
import gb.spring.emarket.auth.service.UserService;
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
@RequestMapping("api/v1")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequestDTO authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new AuthWebServiceError("Incorrect username or password", AuthWebServiceError.AuthServiceErrors.BAD_CREDENTIALS.toString()), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateNewToken(userDetails);
        return ResponseEntity.ok(new AuthResponseDTO(token, userDetails.getAuthorities()));
    }
}
