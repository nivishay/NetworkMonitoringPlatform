package com.nivishay.nmp.targets.service;

import com.nivishay.nmp.targets.api.dto.TargetCollectionDetailsResponse;
import com.nivishay.nmp.targets.repository.TargetCollectionMemberRepository;
import com.nivishay.nmp.targets.repository.TargetCollectionRepository;
import com.nivishay.nmp.targets.service.exeptions.CollectionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TargetCollectionQueryService {

    private final TargetCollectionRepository collectionRepo;
    private final TargetCollectionMemberRepository memberRepo;

    public TargetCollectionQueryService(TargetCollectionRepository collectionRepo,
                                        TargetCollectionMemberRepository memberRepo) {
        this.collectionRepo = collectionRepo;
        this.memberRepo = memberRepo;
    }

    public TargetCollectionDetailsResponse getById(UUID ownerUserId, UUID collectionId) {
        var c = collectionRepo.findByIdAndOwnerUserId(collectionId, ownerUserId)
                .orElseThrow(() -> new CollectionNotFoundException("collection not found"));

        var targets = memberRepo.findTargetsByCollectionId(collectionId);

        return new TargetCollectionDetailsResponse(
                c.getId(),
                c.getName(),
                c.getCreatedAt(),
                targets
        );
    }
}