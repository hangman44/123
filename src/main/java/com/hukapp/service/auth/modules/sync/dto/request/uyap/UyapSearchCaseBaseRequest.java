package com.hukapp.service.auth.modules.sync.dto.request.uyap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UyapSearchCaseBaseRequest {
    
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
}
