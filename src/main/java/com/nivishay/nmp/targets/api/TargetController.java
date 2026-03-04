package com.nivishay.nmp.targets.api;

import com.nivishay.nmp.targets.api.dto.CreateTargetRequest;
import com.nivishay.nmp.targets.api.dto.CreateOrGetTargetResponse;
import com.nivishay.nmp.targets.service.TargetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/targets")
public class TargetController {

    private final TargetService targetService;

    public TargetController(TargetService targetService) {
        this.targetService = targetService;
    }

    @GetMapping
    public ResponseEntity<List<CreateOrGetTargetResponse>> getAllTargets(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        List<CreateOrGetTargetResponse> response = targetService.getAllForUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateOrGetTargetResponse> getTargetById(Authentication authentication,
                                                                   @PathVariable UUID id) {
        UUID userId = UUID.fromString(authentication.getName());
        CreateOrGetTargetResponse response = targetService.getTargetById(userId,id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")//target id
    public ResponseEntity<Void> deleteTargetById(Authentication authentication,
                                                 @PathVariable UUID id) {
        UUID userId = UUID.fromString(authentication.getName());
        targetService.deleteForTargetById(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreateOrGetTargetResponse> createTarget(Authentication authentication,
                                                                  @RequestBody CreateTargetRequest request) {
        UUID userId = UUID.fromString(authentication.getName());
        CreateOrGetTargetResponse created = targetService.createTarget(userId, request);
        return ResponseEntity
                .created(URI.create("/api/targets/" + created.id()))
                .body(created);
    }
}