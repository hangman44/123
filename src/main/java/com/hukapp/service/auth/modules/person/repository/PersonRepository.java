package com.hukapp.service.auth.modules.person.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hukapp.service.auth.modules.person.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByIdentityNumber(Long identityNumber);

    Optional<Person> findByEmail(String email);
}