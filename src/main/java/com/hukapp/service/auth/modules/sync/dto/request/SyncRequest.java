package com.hukapp.service.auth.modules.sync.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyncRequest {

    @NotBlank
    private String jsid;

}
