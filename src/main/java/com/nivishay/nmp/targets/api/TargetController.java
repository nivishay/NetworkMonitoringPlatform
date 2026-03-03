package com.nivishay.nmp.targets.api;

import com.nivishay.nmp.targets.api.dto.CreateTargetRequest;
import com.nivishay.nmp.targets.api.dto.GetAllResponse;
import com.nivishay.nmp.targets.api.dto.createTargetResponse;
import com.nivishay.nmp.targets.service.TargetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/targets")
public class TargetController {

    private final TargetService targetService;

    public TargetController(TargetService targetService) {
        this.targetService = targetService;
    }

    @GetMapping
    public ResponseEntity<GetAllResponse> getAllTargets(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        GetAllResponse response = targetService.getAllForUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TargetResponse> getTargetById(Authentication authentication,
                                                        @PathVariable UUID id) {
        UUID userId = UUID.fromString(authentication.getName());
        TargetResponse response = targetService.getTargetById(userId,id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTargetById(Authentication authentication,
                                                 @PathVariable UUID id) {
        UUID userId = UUID.fromString(authentication.getName());
        targetService.deleteForUser(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<createTargetResponse> createTarget(Authentication authentication,
                                                       @RequestBody CreateTargetRequest request) {
        UUID userId = UUID.fromString(authentication.getName());
        createTargetResponse created = targetService.createTarget(userId, request);
        return ResponseEntity
                .created(URI.create("/api/targets/" + created.id()))
                .body(created);
    }
}