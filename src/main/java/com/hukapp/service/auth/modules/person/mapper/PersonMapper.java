package com.hukapp.service.auth.modules.person.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.hukapp.service.auth.modules.person.dto.request.PersonCreateRequest;
import com.hukapp.service.auth.modules.person.dto.response.PersonCreateResponse;
import com.hukapp.service.auth.modules.person.entity.Person;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    builder = @Builder(disableBuilder = true)
)
public interface PersonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Person toEntity(PersonCreateRequest request);

    PersonCreateResponse toDTO(Person person);
}