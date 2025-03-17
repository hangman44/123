package com.hukapp.service.auth.modules.sync.dto.request.uyap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UyapSearchCaseRequest extends UyapSearchCaseBaseRequest {

    private int dosyaDurumKod;
    private int pageSize;
    private int pageNumber;

    public static UyapSearchCaseRequest activeCaseSearchRequest() {
        UyapSearchCaseRequest req = caseSearchRequestWithoutCaseStatus();
        req.setDosyaDurumKod(0);
        return req;
    }

    public static UyapSearchCaseRequest closedCaseSearchRequest() {
        UyapSearchCaseRequest req = caseSearchRequestWithoutCaseStatus();
        req.setDosyaDurumKod(1);
        return req;
    }

    private static UyapSearchCaseRequest caseSearchRequestWithoutCaseStatus() {
        return UyapSearchCaseRequest.builder()
                .pageSize(1000)
                .pageNumber(1)
                .build();
    }
    
}
