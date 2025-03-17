package com.hukapp.service.auth.modules.sync.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hukapp.service.auth.common.dto.response.BaseResponse;
import com.hukapp.service.auth.modules.sync.dto.request.SyncRequest;
import com.hukapp.service.auth.modules.sync.service.UyapService;

@Slf4j
@RestController
@RequestMapping("api/sync")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class SyncController {

    private final UyapService uyapService;

    @PostMapping
    @Operation(summary = "Sync UYAP", description = "Sync to UYAP system using provided JSID. Requires authentication with Bearer token")
    public BaseResponse syncToUyap(@RequestBody @Validated SyncRequest request, Authentication authentication) {

        /* Get the JWT token and subject */
        Jwt jwt = (Jwt) authentication.getPrincipal();

        /*
         * Subject icerisinde kullanici email'i bulunmaktadir.
         * TODO: Bu email ile uyap'ta kullanilan email eslesme kontrol yapilabilir.
         */
        String subject = jwt.getSubject();
        log.debug("Sync request received for user: '{}'", subject);

        return uyapService.syncWithUyap(subject, request.getJsid());

    }

}
