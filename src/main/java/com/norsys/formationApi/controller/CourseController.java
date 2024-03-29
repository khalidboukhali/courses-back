package com.norsys.formationApi.controller;

import com.norsys.formationApi.dto.requestDto.AdherentRequest;
import com.norsys.formationApi.dto.responseDto.AdherentResponse;
import com.norsys.formationApi.dto.responseDto.ResponseDto;
import com.norsys.formationApi.dto.requestDto.CourseRequest;
import com.norsys.formationApi.dto.responseDto.CourseResponse;
import com.norsys.formationApi.service.AdherentService;
import com.norsys.formationApi.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    private final AdherentService adherentService;

    public CourseController(CourseService courseService, AdherentService adherentService) {
        this.courseService = courseService;
        this.adherentService = adherentService;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses(){
        List<CourseResponse> courses = this.courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseTitle}")
    public ResponseEntity<List<CourseResponse>> getCourseByTitle(@PathVariable String courseTitle){
        List<CourseResponse> courses = this.courseService.getCourseByTitle(courseTitle);
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addNewCourse(@RequestBody @Valid CourseRequest courseRequest){
        try {
            this.courseService.addNewCourse(courseRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(200,"New course added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(400,"Error adding new course: " + e.getMessage()));
        }
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<ResponseDto> updateCourse(@PathVariable long courseId,
                                                     @RequestBody @Valid CourseRequest courseRequest){
        try {
            this.courseService.updateCourse(courseId, courseRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(200,"Course updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(400,"Error updating course: " + e.getMessage()));
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ResponseDto> deleteCourse(@PathVariable long id){
        try {
            this.courseService.deleteCourse(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(200,"courses deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(400,"Error deleting course: " + e.getMessage()));
        }
    }

    @PostMapping("/{courseId}/adherents")
    public ResponseEntity<ResponseDto> addNewAdherentToCourse(@RequestBody @Valid AdherentRequest adherentRequest, @PathVariable long courseId){
        try {
            this.courseService.addNewAdherentToCourse(courseId, adherentRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(200,"New adherent added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(400,"Error adding new adherent: " + e.getMessage()));
        }
    }

    @DeleteMapping("{courseId}/adherents/{adherentId}")
    public ResponseEntity<ResponseDto> removeAdherentFromCourse(@PathVariable Long courseId, @PathVariable Long adherentId) {
        try {
            this.courseService.removeAdherentFromCourse(courseId, adherentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(200,"Adherent removed from course successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(400,"An error occurred while removing adherent from course: " + e.getMessage()));
        }
    }

    @GetMapping("{courseId}/adherents")
    public ResponseEntity<List<AdherentResponse>> getAllAdherentsByCourseId(@PathVariable long courseId) {
        List<AdherentResponse> adherents = adherentService.getAllAdherentsByCourseId(courseId);
        return ResponseEntity.ok(adherents);
    }

    @PutMapping("adherents/{adherentId}")
    public ResponseEntity<ResponseDto> updateAdherent(@PathVariable long adherentId,
                                                      @RequestBody @Valid AdherentRequest adherentRequest){
        try {
            this.adherentService.updateAdherent(adherentId, adherentRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(200,"Adherent updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(400,"Error updating adherent: " + e.getMessage()));
        }
    }

}
