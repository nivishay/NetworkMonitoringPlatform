package com.nivishay.nmp.targets.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "target_collection_members")
public class TargetCollectionMember {

    @EmbeddedId
    private Id id;

    @Column(name = "added_at", nullable = false)
    private Instant addedAt;

    protected TargetCollectionMember() {}

    public TargetCollectionMember(UUID collectionId, UUID targetId) {
        this.id = new Id(collectionId, targetId);
        this.addedAt = Instant.now();
    }

    public Id getId() { return id; }
    public Instant getAddedAt() { return addedAt; }

    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "collection_id", nullable = false)
        private UUID collectionId;

        @Column(name = "target_id", nullable = false)
        private UUID targetId;

        protected Id() {}

        public Id(UUID collectionId, UUID targetId) {
            this.collectionId = collectionId;
            this.targetId = targetId;
        }

        public UUID getCollectionId() { return collectionId; }
        public UUID getTargetId() { return targetId; }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id other)) return false;
            return Objects.equals(collectionId, other.collectionId)
                    && Objects.equals(targetId, other.targetId);
        }

        @Override public int hashCode() {
            return Objects.hash(collectionId, targetId);
        }
    }
}