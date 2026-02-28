package com.nivishay.nmp.users.service;

import com.nivishay.nmp.users.api.dto.UserResponse;
import com.nivishay.nmp.users.domain.User;
import com.nivishay.nmp.users.domain.UserRole;
import com.nivishay.nmp.users.repository.UserRepository;
import com.nivishay.nmp.users.service.exception.EmailAlreadyExistsException;
import com.nivishay.nmp.users.service.exception.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UUID createUser(String email, String password) {
        //TODO change password hash from raw
        email = normalizeEmail(email);
        String hashedPassword = passwordEncoder.encode(password);
        if(userRepository.existsByEmail(email))
        {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User(email, hashedPassword, UserRole.USER);
        try {
            User saved = userRepository.save(user);
            return saved.getId();
        } catch (DataIntegrityViolationException ex) {
            // זה קורה אם ה-UNIQUE ב-DB נשבר (כפילות)
            //TODO i dont know if this error is good to throw
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }
    public UserResponse getByID(UUID id) throws UserNotFoundException {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserResponse(
                user.getId().toString(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return email.trim().toLowerCase();
    }

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponse(
                        user.getId().toString(),
                        user.getEmail(),
                        user.getRole().name()
                ))
                .collect(Collectors.toList());
    }
}
