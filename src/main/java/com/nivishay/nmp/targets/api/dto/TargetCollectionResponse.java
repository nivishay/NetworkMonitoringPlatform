package com.nivishay.nmp.targets.api.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record TargetCollectionResponse(
        UUID id,
        String name,
        Instant createdAt

) {}
