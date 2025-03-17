package com.hukapp.service.auth.modules.sync.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;

import org.apache.commons.lang3.ThreadUtils;
import org.springframework.stereotype.Service;

import com.hukapp.service.auth.common.dto.response.BaseResponse;
import com.hukapp.service.auth.common.exception.custom.AuthException;
import com.hukapp.service.auth.common.util.UyapUtil;
import com.hukapp.service.auth.modules.sync.dto.request.uyap.UyapSearchCaseDetailRequest;
import com.hukapp.service.auth.modules.sync.dto.request.uyap.UyapSearchCaseRequest;
import com.hukapp.service.auth.modules.sync.dto.request.uyap.UyapSearchTrialsRequest;
import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeData;
import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeDataCaseDetails;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;
import com.hukapp.service.auth.modules.sync.service.NoSqlLikeDataService;
import com.hukapp.service.auth.modules.sync.service.UyapService;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.json.JSONObject;

@Service
@Slf4j
@AllArgsConstructor
public class UyapServiceImpl implements UyapService {

    private final NoSqlLikeDataService noSqlLikeDataService;
    private static final long WAIT_TIME_BETWEEN_REQUESTS = 1250;

    @Override
    public BaseResponse syncWithUyap(String subject, String jsid) {

        /* checking UYAP connectivity, if it is not successful, throw an exception. */
        checkUyapConnectivity(subject, jsid);

        log.debug("Started to sync for user '{}'", subject);

        ///////////////////////////////////////////////////////////////////////
        /* syncing no-request-body URLs. */
        syncUrlsWithNoRequestBody(subject, jsid);
        syncUserPhoto(subject, jsid);

        /* syncing URLs which requires request body. */
        syncTrials(subject, jsid);
        syncUserCases(subject, jsid);
        ///////////////////////////////////////////////////////////////////////

        log.debug("Finished syncing for user '{}'", subject);

        return BaseResponse.builder()
                .responseMessage("Senkronizasyon işlemi tamamlandı. Kullanıcı: " + getUserNameAndSurname(jsid)).build();
    }

    private void syncUserCases(String subject, String jsid) {

        DataSourceEnum activeCasesDataSource = DataSourceEnum.UYAP_ACTIVE_CASE_SEARCH;
        HttpResponse<JsonNode> activeCasesResponse = UyapUtil.callUyap(subject, jsid, activeCasesDataSource,
                UyapSearchCaseRequest.activeCaseSearchRequest());
        saveToNoSqlLikeData(subject, activeCasesDataSource, activeCasesResponse);
        JsonNode activeCases = activeCasesResponse.getBody();

        for (int i = 0; i < activeCases.getArray().getJSONArray(0).length(); i++) {

            JSONObject caseInfo = activeCases.getArray().getJSONArray(0).getJSONObject(i);
            String caseNumber = caseInfo.get("dosyaNo").toString();
            String caseId = caseInfo.get("dosyaId").toString();

            log.debug("Case id  : {}", caseId);
            log.debug("Case info: {}", caseNumber);
            log.debug("Started to sync active case history for case number: {}", caseInfo.getString("dosyaNo"));

            HttpResponse<JsonNode> caseHistoryResponse = UyapUtil.callUyap(subject, jsid,
                    DataSourceEnum.UYAP_CASE_HISTORY,
                    new UyapSearchCaseDetailRequest(caseId));
            
            saveToNoSqlLikeDataCaseDetails(subject, DataSourceEnum.UYAP_CASE_HISTORY, caseNumber, caseHistoryResponse, false);

            HttpResponse<JsonNode> caseTahsilatReddiyatResponse = UyapUtil.callUyap(subject, jsid,
                    DataSourceEnum.UYAP_CASE_TAHSILAT_REDDIYAT,
                    new UyapSearchCaseDetailRequest(caseId));

            saveToNoSqlLikeDataCaseDetails(subject, DataSourceEnum.UYAP_CASE_TAHSILAT_REDDIYAT, caseNumber, caseTahsilatReddiyatResponse, false);

            HttpResponse<JsonNode> caseTaraflarResponse = UyapUtil.callUyap(subject, jsid, DataSourceEnum.UYAP_CASE_TARAFLAR,
                    new UyapSearchCaseDetailRequest(caseId));

            saveToNoSqlLikeDataCaseDetails(subject, DataSourceEnum.UYAP_CASE_TARAFLAR, caseNumber, caseTaraflarResponse, false);

            ThreadUtils.sleepQuietly(Duration.ofMillis(WAIT_TIME_BETWEEN_REQUESTS));

        }

        DataSourceEnum closedCasesDataSource = DataSourceEnum.UYAP_CLOSED_CASE_SEARCH;
        HttpResponse<JsonNode> closedCasesResponse = UyapUtil.callUyap(subject, jsid, closedCasesDataSource,
                UyapSearchCaseRequest.closedCaseSearchRequest());
        saveToNoSqlLikeData(subject, closedCasesDataSource, closedCasesResponse);
        JsonNode closedCases = closedCasesResponse.getBody();

        for (int i = 0; i < closedCases.getArray().getJSONArray(0).length(); i++) {

            JSONObject caseInfo = closedCases.getArray().getJSONArray(0).getJSONObject(i);
            String caseNumber = caseInfo.get("dosyaNo").toString();
            String caseId = caseInfo.get("dosyaId").toString();

            log.debug("Case info: {}", caseNumber);
            log.debug("Started to sync closed case history for case number: {}", caseInfo.getString("dosyaNo"));

            HttpResponse<JsonNode> caseHistoryResponse = UyapUtil.callUyap(subject, jsid,
                    DataSourceEnum.UYAP_CASE_HISTORY,
                    new UyapSearchCaseDetailRequest(caseId));
            
            saveToNoSqlLikeDataCaseDetails(subject, DataSourceEnum.UYAP_CASE_HISTORY, caseNumber, caseHistoryResponse, true);

            HttpResponse<JsonNode> caseTahsilatReddiyatResponse = UyapUtil.callUyap(subject, jsid,
                    DataSourceEnum.UYAP_CASE_TAHSILAT_REDDIYAT,
                    new UyapSearchCaseDetailRequest(caseId));

            saveToNoSqlLikeDataCaseDetails(subject, DataSourceEnum.UYAP_CASE_TAHSILAT_REDDIYAT, caseNumber, caseTahsilatReddiyatResponse, true);

            HttpResponse<JsonNode> caseTaraflarResponse = UyapUtil.callUyap(subject, jsid, DataSourceEnum.UYAP_CASE_TARAFLAR,
                    new UyapSearchCaseDetailRequest(caseId));

            saveToNoSqlLikeDataCaseDetails(subject, DataSourceEnum.UYAP_CASE_TARAFLAR, caseNumber, caseTaraflarResponse, true);

            ThreadUtils.sleepQuietly(Duration.ofMillis(WAIT_TIME_BETWEEN_REQUESTS));

        }

    }

    private void syncTrials(String subject, String jsid) {

        DataSourceEnum dataSource = DataSourceEnum.UYAP_TRIAL_SEARCH;

        UyapSearchTrialsRequest request = new UyapSearchTrialsRequest.Builder()
                .baslangicTarihi(LocalDate.now())
                .bitisTarihi(LocalDate.now().plusDays(30))
                .build();

        HttpResponse<JsonNode> response = UyapUtil.callUyap(subject, jsid, dataSource, request);

        saveToNoSqlLikeData(subject, dataSource, response);
    }

    private void syncUrlsWithNoRequestBody(String subject, String jsid) {
        for (DataSourceEnum dataSource : DataSourceEnum.getNoRequestBodyEnums()) {

            log.debug("Started to sync {} for user '{}'", dataSource, subject);

            HttpResponse<JsonNode> response = UyapUtil.callUyap(subject, jsid, dataSource, null);

            saveToNoSqlLikeData(subject, dataSource, response);
        }
    }

    private void syncUserPhoto(String subject, String jsid) {
        /**
         * Fotograf direk string olarak geldigi icin syncUrlsWithNoRequestBody
         * metodundan ayrildi.
         * 
         * @param subject
         * @param jsid
         */

        DataSourceEnum dataSource = DataSourceEnum.UYAP_USER_PHOTO;

        HttpResponse<String> response = UyapUtil
                .createUyapHttpPostRequest(jsid, dataSource.url())
                .asString();

        saveToNoSqlLikeData(subject, dataSource, response);
    }

    private void checkUyapConnectivity(String subject, String jsid) {

        log.debug("Checking Uyap connectivity for user '{}'", subject);

        HttpResponse<JsonNode> response = UyapUtil.callUyap(subject, jsid, DataSourceEnum.UYAP_USER_DETAILS, null);

        if (!response.isSuccess() || !response.getBody().getObject().has("tcKimlikNo")) {
            throw new AuthException("UYAP entegrasyon basarisiz. Email: " + subject);
        }

        log.debug("Uyap connectivity check is successful for user '{}'", subject);

    }

    private String getUserNameAndSurname(String jsid) {
        HttpResponse<JsonNode> response = UyapUtil.callUyap(null, jsid, DataSourceEnum.UYAP_USER_DETAILS, null);

        return response.getBody().getObject().getString("adi") + " "
                + response.getBody().getObject().getString("soyadi");
    }

    private <T> void saveToNoSqlLikeData(String subject, DataSourceEnum dataSource, HttpResponse<T> response) {
        log.debug("Response status: {} isSuccess {}", response.getStatus(), response.isSuccess());
        if (response.isSuccess() || response.getStatus() == 200) {
            log.debug("Sync is successful for {} for user '{}'", dataSource, subject);
            log.debug("Response: {}", response.getBody());
            noSqlLikeDataService.saveOrUpdate(new NoSqlLikeData(subject, dataSource, response.getBody().toString()));
        } else {
            log.error("Sync is failed for {} for user '{}'", dataSource, subject);
            log.debug("Response: {}", response.getBody());
        }
    }

    private void saveToNoSqlLikeDataCaseDetails(String subject, DataSourceEnum dataSource, String caseNumber,
            HttpResponse<JsonNode> response, boolean isClosedCase) {
        if (response.isSuccess()) {
            log.debug("Sync is successful for {} for user '{}'", dataSource, subject);
            log.debug("Response: {}", response.getBody());
            noSqlLikeDataService.saveOrUpdate(new NoSqlLikeDataCaseDetails(subject, dataSource, caseNumber,
                    response.getBody().toString(),isClosedCase));
        } else {
            log.error("Sync is failed for {} for user '{}'", dataSource, subject);
            log.debug("Response: {}", response.getBody());
        }
    }

}
