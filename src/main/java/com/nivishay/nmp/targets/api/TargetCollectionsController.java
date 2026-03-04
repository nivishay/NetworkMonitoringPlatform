package com.nivishay.nmp.targets.api;

import com.nivishay.nmp.targets.api.dto.CreateTargetCollectionRequest;
import com.nivishay.nmp.targets.api.dto.TargetCollectionDetailsResponse;
import com.nivishay.nmp.targets.api.dto.TargetCollectionResponse;
import com.nivishay.nmp.targets.api.dto.TargetInCollectionResponse;
import com.nivishay.nmp.targets.service.TargetCollectionQueryService;
import com.nivishay.nmp.targets.service.TargetCollectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/target-collections")
public class TargetCollectionsController {

    private final TargetCollectionService service;
    private final TargetCollectionQueryService queryService;

    public TargetCollectionsController(TargetCollectionService service, TargetCollectionQueryService queryService) {
        this.service = service;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<TargetCollectionResponse> create(Authentication auth,
                                                           @Valid @RequestBody CreateTargetCollectionRequest req) {
        UUID userId = UUID.fromString(auth.getName());
        var created = service.create(userId,req.name());
        return ResponseEntity.created(URI.create("/api/target-collections/" + created.id()))
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<TargetCollectionResponse>> getAll(Authentication auth) {
        UUID userId = UUID.fromString(auth.getName());
        return ResponseEntity.ok(service.getAll(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication auth, @PathVariable UUID id) {
        UUID userId = UUID.fromString(auth.getName());
        service.delete(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/targets/{targetId}")
    public ResponseEntity<Void> addTarget(Authentication auth,
                                          @PathVariable UUID id,
                                          @PathVariable UUID targetId) {
        UUID userId = UUID.fromString(auth.getName());
        service.addTarget(userId, id, targetId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/targets/{targetId}")
    public ResponseEntity<Void> removeTarget(Authentication auth,
                                             @PathVariable UUID id,
                                             @PathVariable UUID targetId) {
        UUID userId = UUID.fromString(auth.getName());
        service.removeTarget(userId, id, targetId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/targets")
    public ResponseEntity<List<TargetInCollectionResponse>> getTargets(Authentication auth,
                                                                       @PathVariable UUID id) {
        UUID userId = UUID.fromString(auth.getName());
        return ResponseEntity.ok(queryService.getById(userId, id).targets());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<TargetCollectionDetailsResponse> getById(Authentication auth,
                                                                   @PathVariable UUID id) {
        UUID userId = UUID.fromString(auth.getName());
        return ResponseEntity.ok(queryService.getById(userId, id));
    }
}