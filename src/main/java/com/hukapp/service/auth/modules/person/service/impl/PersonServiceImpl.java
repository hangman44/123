package com.hukapp.service.auth.modules.person.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hukapp.service.auth.common.exception.custom.AuthException;
import com.hukapp.service.auth.common.exception.custom.UnexpectedStatusException;
import com.hukapp.service.auth.modules.person.dto.request.PersonCreateRequest;
import com.hukapp.service.auth.modules.person.dto.request.PersonLoginRequest;
import com.hukapp.service.auth.modules.person.dto.response.PersonCreateResponse;
import com.hukapp.service.auth.modules.person.dto.response.PersonLoginResponse;
import com.hukapp.service.auth.modules.person.entity.Person;
import com.hukapp.service.auth.modules.person.mapper.PersonMapper;
import com.hukapp.service.auth.modules.person.repository.OtpRepository;
import com.hukapp.service.auth.modules.person.repository.PersonRepository;
import com.hukapp.service.auth.modules.person.service.PersonService;

import org.springframework.context.MessageSource;
import java.util.Locale;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final OtpRepository otpRepository;

    // Create
    @Override
    public PersonCreateResponse savePerson(PersonCreateRequest personCreateRequest) {
        // Base entity fields will be auto-populated by JPA auditing
        Person person = personMapper.toEntity(personCreateRequest);
        // Encode the password before saving
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person = personRepository.save(person);
        return personMapper.toDTO(person);
    }

    // Read
    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Optional<Person> getPersonByIdentityNumber(Long identityNumber) {
        return personRepository.findByIdentityNumber(identityNumber);
    }

    @Override
    public Optional<Person> getPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    // Update
    @Override
    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    // Delete
    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public PersonLoginResponse login(PersonLoginRequest personLoginRequest) {
        Person person = personRepository.findByEmail(personLoginRequest.getEmail())
                .orElseThrow(() -> new AuthException(
                        messageSource.getMessage("auth.error.invalidCredentials", null, Locale.getDefault())));

        if (!passwordEncoder.matches(personLoginRequest.getPassword(), person.getPassword())) {
            throw new AuthException(
                    messageSource.getMessage("auth.error.invalidCredentials", null, Locale.getDefault()));
        }

        // TODO: Login sonrasinda kullaniciya login olma islemi ile ilgli bilgilendirme
        // yapilabilir
        // Ornek: Son giris tarihi, son giris yeri, son giris ip adresi, vs.
        return PersonLoginResponse.builder()
                .id(person.getId())
                .name(person.getName())
                .surname(person.getSurname())
                .isNewUser(person.getIsNewUser())
                .isDeleted(person.getIsDeleted())
                .jwt("jwt") // Note: You might want to implement proper JWT token generation here
                .build();
    }

    @Override
    public void updatePassword(String email, String otp, String newPassword) {

        // Check if the OTP is validated and not expired if not throw an exception
        otpRepository.findByEmailAndOtpValueAndIsUsedTrueAndExpirationTimeAfter(email, otp, LocalDateTime.now())
                .orElseThrow(() -> new AuthException(
                        messageSource.getMessage("otp.invalid.or.expired", null, Locale.getDefault())));

        // Find the person by email and update the password if person is not found throw an exception
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new UnexpectedStatusException(
                        messageSource.getMessage("otp.invalid.or.expired", null, Locale.getDefault())));
        
        person.setPassword(passwordEncoder.encode(newPassword));
        personRepository.save(person);

        // TODO: Parola degistirme islemi sonrasinda kullaniciya bilgilendirme yapilabilir
        // Ornek: Parola degistirme islemi basarili, parola degistirme islemi basarisiz, vs.
        
    }

}