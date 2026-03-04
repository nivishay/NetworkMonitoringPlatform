package com.nivishay.nmp.targets.api.dto;

import java.util.UUID;

public record TargetInCollectionResponse(
        UUID id,
        String url
) {}