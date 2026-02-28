package com.nivishay.nmp.auth.api;

import com.nivishay.nmp.auth.api.dto.LoginRequest;
import com.nivishay.nmp.auth.api.dto.LoginResponse;
import com.nivishay.nmp.auth.service.AuthService;
import com.nivishay.nmp.users.service.exception.InvalidCredentialsException;
import com.nivishay.nmp.users.service.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // TODO: Implement authentication and authorization logic here
     private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws UserNotFoundException, InvalidCredentialsException {
        return ResponseEntity.ok(authService.login(request));

    }
}