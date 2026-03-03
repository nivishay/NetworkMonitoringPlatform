package com.nivishay.nmp.targets.api.dto;

import org.hibernate.validator.constraints.URL;

public record CreateTargetRequest(
        @URL String url,
        String type
) {}