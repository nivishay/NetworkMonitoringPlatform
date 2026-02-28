package com.nivishay.nmp.auth.service;

import com.nivishay.nmp.auth.api.dto.LoginRequest;
import com.nivishay.nmp.auth.api.dto.LoginResponse;
import com.nivishay.nmp.auth.infra.JwtProperties;
import com.nivishay.nmp.auth.infra.JwtTokenService;
import com.nivishay.nmp.common.utils.EmailUtils;
import com.nivishay.nmp.users.domain.User;
import com.nivishay.nmp.users.repository.UserRepository;
import com.nivishay.nmp.users.service.exception.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final JwtProperties jwtProperties;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService,
            JwtProperties jwtProperties
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.jwtProperties = jwtProperties;
    }

    public LoginResponse login(LoginRequest request) throws InvalidCredentialsException {
        String email = EmailUtils.normalize(request.email());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtTokenService.generateAccessToken(user);

        long expiresInSeconds = jwtProperties.getTtl() / 1000;

        return new LoginResponse(token,expiresInSeconds);
    }
}