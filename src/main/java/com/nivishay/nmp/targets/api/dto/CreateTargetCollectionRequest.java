package com.nivishay.nmp.targets.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTargetCollectionRequest(
        @NotBlank String name
) {}