package com.norsys.formationApi.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AdherentRequest(
        @Size(min = 3, message = "First name should at least contain {min} characters.")
        String firstName,
        @Size(min = 3, message = "Last name should at least contain {min} characters.")
        String lastName,
        @Email(message = "Please enter a valid email address.")
        String email
) {}
