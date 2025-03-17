package com.hukapp.service.auth.modules.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hukapp.service.auth.modules.app.dto.response.UserPhotoResponse;
import com.hukapp.service.auth.modules.app.service.UyapUserService;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@RequestMapping("api/user")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UyapUserInfoController {

    private final UyapUserService uyapUserService;

    @GetMapping(value = "/photo", produces = "application/json")
    @Operation(summary = "Get user photo", description = "")
    public ResponseEntity<UserPhotoResponse> getUserPhoto(Authentication authentication) {
        return ResponseEntity.ok(uyapUserService.getUserPhoto(authentication.getName()));
    }

    @GetMapping(value = "/details", produces = "application/json;charset=UTF-8")
    @Operation(summary = "Get user details", description = "")
    public ResponseEntity<String> getUserDetails(Authentication authentication) {
        return ResponseEntity.ok(uyapUserService.getUserInfo(authentication.getName(), DataSourceEnum.UYAP_USER_DETAILS));
    }

    @GetMapping(value = "/notifications", produces = "application/json;charset=UTF-8")
    @Operation(summary = "Get user notifications", description = "")
    public ResponseEntity<String> getUserNotifications(Authentication authentication) {
        return ResponseEntity.ok().body(uyapUserService.getUserInfo(authentication.getName(), DataSourceEnum.UYAP_NOTIFICATIONS));
    }

    @GetMapping(value = "/announcements", produces = "application/json;charset=UTF-8")
    @Operation(summary = "Get UYAP announcements", description = "")
    public ResponseEntity<String> getUyapAnnouncements(Authentication authentication) {
        return ResponseEntity.ok(uyapUserService.getUserInfo(authentication.getName(), DataSourceEnum.UYAP_UNEXPIRED_ANNOUNCEMENTS));
    }

    @GetMapping(value = "/cases", produces = "application/json;charset=UTF-8")
    @Operation(summary = "Get user cases by case status", description = "")
    public ResponseEntity<String> getUserCases(Authentication authentication, @RequestParam boolean active) {
        DataSourceEnum dataSource = active ? DataSourceEnum.UYAP_ACTIVE_CASE_SEARCH : DataSourceEnum.UYAP_CLOSED_CASE_SEARCH;
        return ResponseEntity.ok(uyapUserService.getUserInfo(authentication.getName(), dataSource));
    }

    @GetMapping(value = "/trials", produces = "application/json;charset=UTF-8")
    @Operation(summary = "Get user trials", description = "")
    public ResponseEntity<String> getUserTrials(Authentication authentication) {
        return ResponseEntity.ok(uyapUserService.getUserInfo(authentication.getName(), DataSourceEnum.UYAP_TRIAL_SEARCH));
    }

}
