package com.hukapp.service.auth.modules.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hukapp.service.auth.modules.app.dto.response.UserPhotoResponse;
import com.hukapp.service.auth.modules.app.service.UyapCaseService;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/case")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UyapCaseDetailsController {

    private final UyapCaseService uyapCaseService;

    @GetMapping(value = "/taraflar", produces = "application/json;charset=UTF-8")
    @Operation(summary = "Get case parties", description = "")
    public ResponseEntity<String> getCaseParties(Authentication authentication, @RequestParam String caseNumber) {
        return ResponseEntity.ok(
                uyapCaseService.getCaseInfo(authentication.getName(), DataSourceEnum.UYAP_CASE_TARAFLAR, caseNumber));

    }

    @GetMapping(value = "/tahsilat-reddiyat", produces = "application/json;charset=UTF-8")
    @Operation(summary = "Get case collections and denials", description = "")
    public ResponseEntity<String> getCaseCollectionAndDenial(Authentication authentication, @RequestParam String caseNumber) {
        return ResponseEntity.ok(
                uyapCaseService.getCaseInfo(authentication.getName(), DataSourceEnum.UYAP_CASE_TAHSILAT_REDDIYAT, caseNumber));

    }

    @GetMapping(value = "/safahat", produces = "application/json;charset=UTF-8")
    @Operation(summary = "Get case history", description = "")
    public ResponseEntity<String> getCaseHistory(Authentication authentication, @RequestParam String caseNumber) {
        return ResponseEntity.ok(
                uyapCaseService.getCaseInfo(authentication.getName(), DataSourceEnum.UYAP_CASE_HISTORY, caseNumber));

    }

}
