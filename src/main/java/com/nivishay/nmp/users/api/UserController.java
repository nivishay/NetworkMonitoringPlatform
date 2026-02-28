package com.nivishay.nmp.users.api;

import com.nivishay.nmp.users.api.dto.CreateUserRequest;
import com.nivishay.nmp.users.api.dto.CreateUserResponse;
import com.nivishay.nmp.users.api.dto.UserResponse;
import com.nivishay.nmp.users.service.UserService;
import com.nivishay.nmp.users.service.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        UUID id = userService.createUser(
                request.email(),
                request.password()
        );
        return ResponseEntity
                .created(URI.create("/api/users/" + id))
                .body(new CreateUserResponse(id));
    }
    @GetMapping("/get")
    public ResponseEntity<List<UserResponse>> getALLUsers() {
        List<UserResponse> users =  userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) throws UserNotFoundException {
        UserResponse user;
        user = userService.getByID(id);
        return ResponseEntity.ok(user);
    }
}
