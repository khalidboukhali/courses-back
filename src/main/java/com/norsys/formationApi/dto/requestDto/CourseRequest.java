package com.norsys.formationApi.dto.requestDto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CourseRequest(
        @Size(min = 3, message = "Name should at least contain {min} characters")
        String title,
        String description,
        @NotNull(message = "Begin date cannot be empty.")
        @FutureOrPresent(message = "Begin date must be in the future or present.")
        LocalDate startDate,
        @NotNull(message = "End date cannot be empty.")
        @FutureOrPresent(message = "End date must be in the future or present.")
        LocalDate endDate
){}
