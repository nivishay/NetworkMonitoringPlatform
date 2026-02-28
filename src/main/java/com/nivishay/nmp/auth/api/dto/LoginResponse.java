package com.nivishay.nmp.auth.api.dto;

public record LoginResponse(
        String accessToken,
        long expiresIn
) {}