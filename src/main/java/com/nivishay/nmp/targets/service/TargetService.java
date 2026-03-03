package com.nivishay.nmp.targets.service;

import com.nivishay.nmp.common.utils.UrlUtils;
import com.nivishay.nmp.targets.api.dto.CreateTargetRequest;
import com.nivishay.nmp.targets.api.dto.GetAllResponse;
import com.nivishay.nmp.targets.api.dto.createTargetResponse;
import com.nivishay.nmp.targets.domain.Target;
import com.nivishay.nmp.targets.repository.TargetRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class TargetService {
    private final TargetRepository targetRepository;
    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }
    public createTargetResponse createTarget(UUID userId, CreateTargetRequest request) {
        UrlUtils.validateHttpUrl(request.url());
        targetRepository.save(new Target(userId, request.url(), request.type()));
        return new createTargetResponse(userId, request.url(), request.type(), Instant.now());
    }

    public GetAllResponse getAll(String userId) {
        return new GetAllResponse();
    }

    public GetAllResponse getAllForUser(UUID userId) {
        return new GetAllResponse();
    }

    public createTargetResponse getTargetById(UUID userId, UUID id) {

    }
}
