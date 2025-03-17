package com.hukapp.service.auth.modules.app.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hukapp.service.auth.common.exception.custom.ResourceNotFoundException;
import com.hukapp.service.auth.modules.app.dto.response.UserPhotoResponse;
import com.hukapp.service.auth.modules.app.repository.UyapUserRepository;
import com.hukapp.service.auth.modules.app.service.UyapUserService;
import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeData;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class UyapUserServiceImpl implements UyapUserService {

    private final UyapUserRepository uyapUserRepository;

    @Override
    public UserPhotoResponse getUserPhoto(String subject) {
        Optional<NoSqlLikeData> data = uyapUserRepository.findByEmailAndDataSource(subject,
                DataSourceEnum.UYAP_USER_PHOTO);
        if (data.isPresent()) {
            return UserPhotoResponse.builder().photo(data.get().getJsonData()).build();
        } else {
            throw new ResourceNotFoundException("User photo not found for: " + subject);
        }
    }

    @Override
    public String getUserInfo(String subject, DataSourceEnum dataSource) {
        Optional<NoSqlLikeData> data = uyapUserRepository.findByEmailAndDataSource(subject,
                dataSource);
        if (data.isPresent()) {
            return data.get().getJsonData();
        } else {
            throw new ResourceNotFoundException("User info not found for: " + subject);
        }
    }
}
