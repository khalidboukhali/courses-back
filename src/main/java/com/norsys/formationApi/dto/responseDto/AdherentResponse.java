package com.norsys.formationApi.dto.responseDto;

public record AdherentResponse(
        long id,
        String firstName,
        String lastName,
        String email
) {}
