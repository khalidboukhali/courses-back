package com.norsys.formationApi.mapper;

import com.norsys.formationApi.dto.requestDto.AdherentRequest;
import com.norsys.formationApi.dto.responseDto.AdherentResponse;
import com.norsys.formationApi.entities.Adherent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdherentMapper {
    AdherentMapper INSTANCE = Mappers.getMapper(AdherentMapper.class);
    AdherentResponse toDto(Adherent adherent);
    Adherent toEntity(AdherentRequest adherentRequest);
}
