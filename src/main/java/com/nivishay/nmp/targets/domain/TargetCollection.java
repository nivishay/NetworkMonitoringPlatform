package com.nivishay.nmp.targets.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "target_collections")
public class TargetCollection {

    @Id
    private UUID id;

    @Column(name = "owner_user_id", nullable = false)
    private UUID ownerUserId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected TargetCollection() {}

    public TargetCollection(UUID ownerUserId, String name) {
        this.id = UUID.randomUUID();
        this.ownerUserId = ownerUserId;
        this.name = name;
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getOwnerUserId() { return ownerUserId; }
    public String getName() { return name; }
    public Instant getCreatedAt() { return createdAt; }

    public void rename(String name) {
        this.name = name;
    }
}