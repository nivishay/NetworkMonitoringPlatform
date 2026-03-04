package com.nivishay.nmp.targets.service;

import com.nivishay.nmp.targets.api.dto.TargetCollectionResponse;
import com.nivishay.nmp.targets.domain.TargetCollection;
import com.nivishay.nmp.targets.domain.TargetCollectionMember;
import com.nivishay.nmp.targets.repository.TargetCollectionMemberRepository;
import com.nivishay.nmp.targets.repository.TargetCollectionRepository;
import com.nivishay.nmp.targets.repository.TargetRepository;
import com.nivishay.nmp.targets.domain.Target;
import com.nivishay.nmp.targets.service.exeptions.CollectionNotFoundException;
import com.nivishay.nmp.targets.service.exeptions.ForbiddenExeption;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TargetCollectionService {

    private final TargetCollectionRepository collectionRepo;
    private final TargetCollectionMemberRepository memberRepo;
    private final TargetRepository targetRepo;

    public TargetCollectionService(TargetCollectionRepository collectionRepo,
                                   TargetCollectionMemberRepository memberRepo,
                                   TargetRepository targetRepo) {
        this.collectionRepo = collectionRepo;
        this.memberRepo = memberRepo;
        this.targetRepo = targetRepo;
    }

    @Transactional
    public TargetCollectionResponse create(UUID ownerUserId, String name) {
        var c = new TargetCollection(ownerUserId, name);
        collectionRepo.save(c);
        return toResponse(c);
    }

    public List<TargetCollectionResponse> getAll(UUID ownerUserId) {
        return collectionRepo.findAllByOwnerUserId(ownerUserId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void delete(UUID ownerUserId, UUID collectionId) {
        var c = collectionRepo.findByIdAndOwnerUserId(collectionId, ownerUserId)
                .orElseThrow(() -> new CollectionNotFoundException("collection not found"));
        collectionRepo.delete(c);
    }

    @Transactional
    public void addTarget(UUID ownerUserId, UUID collectionId, UUID targetId) {
        // 1) ensure collection belongs to user
        Optional<TargetCollection>   TargetCollection = collectionRepo.findByIdAndOwnerUserId(collectionId, ownerUserId);
        if (TargetCollection.isEmpty()) {
            throw new CollectionNotFoundException("collection not found");
        }

        // 2) ensure target belongs to user
        Target t = targetRepo.findById(targetId)
                .orElseThrow(() -> new CollectionNotFoundException("target not found"));
        if (!t.getOwnerUserId().equals(ownerUserId)) {
            throw new ForbiddenExeption("target not owned");
        }

        // 3) insert membership (idempotent)
        var id = new TargetCollectionMember.Id(collectionId, targetId);
        if (!memberRepo.existsById(id)) {
            memberRepo.save(new TargetCollectionMember(collectionId, targetId));
        }
    }

    @Transactional
    public void removeTarget(UUID ownerUserId, UUID collectionId, UUID targetId) {
        collectionRepo.findByIdAndOwnerUserId(collectionId, ownerUserId)
                .orElseThrow(() -> new CollectionNotFoundException("collection not found"));

        // אפשר גם לוודא שה-target שייך למשתמש (לא חובה למחיקה אם עושים delete by ids)
        memberRepo.deleteByIdCollectionIdAndIdTargetId(collectionId, targetId);
    }

    private TargetCollectionResponse toResponse(TargetCollection c) {
        return new TargetCollectionResponse(c.getId(), c.getName(), c.getCreatedAt());
    }
}