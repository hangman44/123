package com.hukapp.service.auth.modules.app.dto.response;

import com.hukapp.service.auth.common.dto.response.BaseResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UserPhotoResponse extends BaseResponse {

    private String photo;
    
}
