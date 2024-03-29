package com.norsys.formationApi.mapper;

import com.norsys.formationApi.dto.requestDto.CourseRequest;
import com.norsys.formationApi.dto.responseDto.CourseResponse;
import com.norsys.formationApi.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
    CourseResponse toDto(Course course);
    Course toEntity(CourseRequest courseRequest);
}
