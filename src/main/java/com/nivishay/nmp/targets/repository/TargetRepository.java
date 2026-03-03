package com.nivishay.nmp.targets.repository;

import com.nivishay.nmp.targets.domain.Target;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TargetRepository extends JpaRepository<Target, UUID> {
    List<Target> findAllByOwnerUserId(UUID ownerUserId);
}