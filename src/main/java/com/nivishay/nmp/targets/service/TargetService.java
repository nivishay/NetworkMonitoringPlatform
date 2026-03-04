package com.nivishay.nmp.targets.service;

import com.nivishay.nmp.common.utils.UrlUtils;
import com.nivishay.nmp.targets.api.dto.CreateTargetRequest;
import com.nivishay.nmp.targets.api.dto.CreateOrGetTargetResponse;
import com.nivishay.nmp.targets.domain.Target;
import com.nivishay.nmp.targets.repository.TargetRepository;
import com.nivishay.nmp.targets.service.exeptions.TargetNotFoundException;
import com.nivishay.nmp.targets.service.exeptions.ForbiddenExeption;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TargetService {
    private final TargetRepository targetRepository;
    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }
    public CreateOrGetTargetResponse createTarget(UUID userId, CreateTargetRequest request) {
        UrlUtils.validateHttpUrl(request.url());

        Target saved = targetRepository.save(new Target(userId, request.url(), request.type()));

        return new CreateOrGetTargetResponse(
                saved.getId(),
                saved.getUrl(),
                saved.getType(),
                saved.getCreatedAt()
        );
    }

    public List<CreateOrGetTargetResponse> getAllForUser(UUID userId) {
        return targetRepository.findAllByOwnerUserId(userId).stream()
                .map(t -> new CreateOrGetTargetResponse(t.getId(), t.getUrl(), t.getType(), t.getCreatedAt()))
                .toList();
    }

    public CreateOrGetTargetResponse getTargetById(UUID userId, UUID id) throws ForbiddenExeption, TargetNotFoundException {
        Optional<Target> target = targetRepository.findById(id);
        if (target.isEmpty()) {
            throw new TargetNotFoundException("Target not found");
        }

        if (!target.get().getOwnerUserId().equals(userId)) {
            throw new ForbiddenExeption("Not your target");
        }

        return new CreateOrGetTargetResponse(
                target.get().getId(),
                target.get().getUrl(),
                target.get().getType(),
                target.get().getCreatedAt()
        );
    }

    public void deleteForTargetById(UUID userId, UUID id) throws TargetNotFoundException, ForbiddenExeption {
        Optional<Target> target = targetRepository.findById(id);
        if (target.isEmpty()) {
            throw new TargetNotFoundException("Target not found");
        }
        if (!target.get().getOwnerUserId().equals(userId)) {
            throw new ForbiddenExeption("Not your target");
        }
        targetRepository.delete(target.get());
    }
}
