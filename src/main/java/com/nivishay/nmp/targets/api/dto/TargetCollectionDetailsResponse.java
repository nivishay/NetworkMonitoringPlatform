package com.nivishay.nmp.targets.api.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record TargetCollectionDetailsResponse(
        UUID id,
        String name,
        Instant createdAt,
        List<TargetInCollectionResponse> targets
) {}
