package com.hukapp.service.auth.modules.person.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import com.hukapp.service.auth.common.converter.StringEncryptionConverter;
import com.hukapp.service.auth.common.entity.BaseEntity;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Person extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Convert(converter = StringEncryptionConverter.class)
    private String personInfoText;

    @Column(nullable = false, unique = true)
    private Long identityNumber;

    private LocalDate birthDate;

    @Column(nullable = false, unique = true)
    private String email;

    @Builder.Default
    private Boolean isEmailVerified = false;

    @Column(nullable = false)
    private String mobilePhone;

    @Builder.Default
    private Boolean isMobilePhoneVerified = false;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    private Boolean isNewUser = true;
}