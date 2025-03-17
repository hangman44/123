package com.hukapp.service.auth.common.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    private String responseMessage;

    @Builder.Default
    private Instant timestamp = Instant.now();

    public BaseResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }
     
}
