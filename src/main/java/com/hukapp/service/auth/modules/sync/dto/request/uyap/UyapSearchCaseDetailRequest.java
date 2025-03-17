package com.hukapp.service.auth.modules.sync.dto.request.uyap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UyapSearchCaseDetailRequest extends UyapSearchCaseBaseRequest {

    private String dosyaId;
    
}
