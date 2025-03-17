package com.hukapp.service.auth.modules.person.dto.response;

import com.hukapp.service.auth.common.dto.response.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpValidationResponse extends BaseResponse {
    private String jwt;
}
