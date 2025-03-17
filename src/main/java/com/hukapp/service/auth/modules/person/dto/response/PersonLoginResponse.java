package com.hukapp.service.auth.modules.person.dto.response;

import com.hukapp.service.auth.common.dto.response.BaseResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Person Response DTO")
public class PersonLoginResponse extends BaseResponse {

    private Long id;
    private String name;
    private String surname;
    private Boolean isNewUser;
    private Boolean isDeleted;
    private String jwt;

}
