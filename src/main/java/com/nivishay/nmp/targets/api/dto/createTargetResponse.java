package com.nivishay.nmp.targets.api.dto;

import java.time.Instant;
import java.util.UUID;

public record createTargetResponse(
        UUID id,
        String url,
        String type,
        Instant createdAt
) {}