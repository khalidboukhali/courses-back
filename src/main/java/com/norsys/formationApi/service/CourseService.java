package com.norsys.formationApi.service;

import com.norsys.formationApi.dto.requestDto.AdherentRequest;
import com.norsys.formationApi.dto.requestDto.CourseRequest;
import com.norsys.formationApi.dto.responseDto.CourseResponse;
import com.norsys.formationApi.entities.Adherent;
import com.norsys.formationApi.entities.Course;
import com.norsys.formationApi.exception.EntityNotFoundException;
import com.norsys.formationApi.mapper.AdherentMapper;
import com.norsys.formationApi.mapper.CourseMapper;
import com.norsys.formationApi.repository.AdherentRepository;
import com.norsys.formationApi.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final AdherentRepository adherentRepository;

    public CourseService(CourseRepository courseRepository, AdherentRepository adherentRepository) {
        this.courseRepository = courseRepository;
        this.adherentRepository = adherentRepository;
    }

    public List<CourseResponse> getAllCourses(){
        List<Course> courseList = this.courseRepository.findAll();
        return courseList.stream()
                .map(CourseMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<CourseResponse> getCourseByTitle(String courseTitle){
        if(courseTitle.trim().equals("")) return getAllCourses();
        List<Course> courseList = this.courseRepository.findAll()
                .stream().filter(course -> course.getTitle().toLowerCase().contains(courseTitle.toLowerCase()))
                .toList();
        return courseList.stream()
                .map(CourseMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public void addNewCourse(CourseRequest courseRequest){
        Course newCourse = CourseMapper.INSTANCE.toEntity(courseRequest);
        this.courseRepository.save(newCourse);
    }

    public void updateCourse(long id, CourseRequest updatedCourse){
        Course oldCourse = this.courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course with id " + id + " not found"));
        oldCourse.setTitle(updatedCourse.title());
        oldCourse.setDescription(updatedCourse.description());
        oldCourse.setStartDate(updatedCourse.startDate());
        oldCourse.setEndDate(updatedCourse.endDate());
        this.courseRepository.save(oldCourse);
    }

    public void deleteCourse(long id) {
        Optional<Course> optionalCourse = this.courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            for (Adherent adherent : course.getAdherents()) {
                adherent.getCourses().remove(course);
            }
            this.courseRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Course with id " + id + " not found");
        }
    }

    public void addNewAdherentToCourse(Long courseId, AdherentRequest adherentRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Adherent newAdherent = AdherentMapper.INSTANCE.toEntity(adherentRequest);

        if (course.getAdherents().contains(newAdherent)) {
            throw new IllegalArgumentException("Adherent is already associated with the course");
        }

        newAdherent.getCourses().add(course);
        course.getAdherents().add(newAdherent);
        adherentRepository.save(newAdherent);
        courseRepository.save(course);
    }

    public void removeAdherentFromCourse(Long courseId, Long adherentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Adherent adherent = adherentRepository.findById(adherentId)
                .orElseThrow(() -> new EntityNotFoundException("Adherent not found"));

        if (!course.getAdherents().contains(adherent)) {
            throw new IllegalArgumentException("Adherent is not associated with the provided course");
        }

        adherent.getCourses().remove(course);
        course.getAdherents().remove(adherent);

        adherentRepository.save(adherent);
        courseRepository.save(course);

        if (adherent.getCourses().isEmpty()) {
            adherentRepository.delete(adherent);
        }
    }

}
