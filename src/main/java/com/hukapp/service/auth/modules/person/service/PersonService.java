package com.hukapp.service.auth.modules.person.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.hukapp.service.auth.modules.person.dto.request.PersonCreateRequest;
import com.hukapp.service.auth.modules.person.dto.request.PersonLoginRequest;
import com.hukapp.service.auth.modules.person.dto.response.PersonCreateResponse;
import com.hukapp.service.auth.modules.person.dto.response.PersonLoginResponse;
import com.hukapp.service.auth.modules.person.entity.Person;

@Component
public interface PersonService {
    PersonCreateResponse savePerson(PersonCreateRequest personCreateRequest);
    List<Person> getAllPersons();
    Optional<Person> getPersonById(Long id);
    Person updatePerson(Person person);
    void deletePerson(Long id);
    Optional<Person> getPersonByIdentityNumber(Long identityNumber);
    Optional<Person> getPersonByEmail(String email);
    PersonLoginResponse login(PersonLoginRequest personLoginRequest);
    void updatePassword(String email, String otp, String newPassword);
}
