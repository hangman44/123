package com.hukapp.service.auth.modules.app.service;

import com.hukapp.service.auth.modules.app.dto.response.UserPhotoResponse;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

public interface UyapUserService {

    UserPhotoResponse getUserPhoto(String subject);

    String getUserInfo(String subject, DataSourceEnum dataSource);
}
