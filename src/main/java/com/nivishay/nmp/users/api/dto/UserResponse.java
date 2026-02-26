package com.nivishay.nmp.users.api.dto;

public record UserResponse(
        String id,
        String email,
        String role
) {}
