package com.nivishay.nmp.targets.repository;

import com.nivishay.nmp.targets.domain.TargetCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TargetCollectionRepository extends JpaRepository<TargetCollection, UUID> {
    List<TargetCollection> findAllByOwnerUserId(UUID ownerUserId);
    Optional<TargetCollection> findByIdAndOwnerUserId(UUID id, UUID ownerUserId);
}