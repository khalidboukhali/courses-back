package com.norsys.formationApi.repository;

import com.norsys.formationApi.entities.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdherentRepository extends JpaRepository<Adherent, Long> {
}
