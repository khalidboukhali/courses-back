package com.norsys.formationApi.service;

import com.norsys.formationApi.dto.requestDto.AdherentRequest;
import com.norsys.formationApi.dto.responseDto.AdherentResponse;
import com.norsys.formationApi.entities.Adherent;
import com.norsys.formationApi.exception.EntityNotFoundException;
import com.norsys.formationApi.mapper.AdherentMapper;
import com.norsys.formationApi.repository.AdherentRepository;
import com.norsys.formationApi.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdherentService {
    private final AdherentRepository adherentRepository;
    private final CourseRepository courseRepository;

    public AdherentService(AdherentRepository adherentRepository, CourseRepository courseRepository) {
        this.adherentRepository = adherentRepository;
        this.courseRepository = courseRepository;
    }

    public List<AdherentResponse> getAllAdherentsByCourseId(long courseId) {
        List<Adherent> adherents = this.courseRepository.findById(courseId).get().getAdherents();
        return adherents.stream()
                .map(AdherentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public void updateAdherent(long id, AdherentRequest updatedAdherent){
        Adherent oldAdherent = this.adherentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adherent with id " + id + " not found"));
        oldAdherent.setFirstName(updatedAdherent.firstName());
        oldAdherent.setLastName(updatedAdherent.lastName());
        oldAdherent.setEmail(updatedAdherent.email());
        this.adherentRepository.save(oldAdherent);
    }
}
