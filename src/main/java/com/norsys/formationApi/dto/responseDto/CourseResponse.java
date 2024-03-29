package com.norsys.formationApi.dto.responseDto;

import java.time.LocalDate;

public record CourseResponse(
        long id,
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate
){}
