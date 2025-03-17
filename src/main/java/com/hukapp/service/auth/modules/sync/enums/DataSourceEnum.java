package com.hukapp.service.auth.modules.sync.enums;

import java.util.List;
import java.util.Arrays;

public enum DataSourceEnum {

    // no request body required
    UYAP_NOTIFICATIONS("https://avukatbeta.uyap.gov.tr/get_kullanici_bildirimleri.ajx", false),
    UYAP_UNEXPIRED_ANNOUNCEMENTS("https://avukatbeta.uyap.gov.tr/getDuyurularYayinda.ajx", false),
    UYAP_USER_PHOTO("https://avukatbeta.uyap.gov.tr/kisiFotografSorgula.ajx", true),
    UYAP_USER_DETAILS("https://avukatbeta.uyap.gov.tr/kullanici_bilgileri.uyap", false),

    // request body required
    UYAP_CASE_HISTORY("https://avukatbeta.uyap.gov.tr/dosya_safahat_bilgileri_brd.ajx", true),
    UYAP_CASE_TAHSILAT_REDDIYAT("https://avukatbeta.uyap.gov.tr/dosya_tahsilat_reddiyat_bilgileri_brd.ajx", true),
    UYAP_CASE_TARAFLAR("https://avukatbeta.uyap.gov.tr/dosya_taraf_bilgileri_brd.ajx", true),
    UYAP_TRIAL_SEARCH("https://avukatbeta.uyap.gov.tr/avukat_durusma_sorgula_brd.ajx", true),
    UYAP_CLOSED_CASE_SEARCH("https://avukatbeta.uyap.gov.tr/search_phrase.ajx", true),
    UYAP_ACTIVE_CASE_SEARCH("https://avukatbeta.uyap.gov.tr/search_phrase.ajx", true);

    private final String url;
    private boolean isRequestBodyRequired = true;

    DataSourceEnum(String url, boolean isRequestBodyRequired) {
        this.url = url;
        this.isRequestBodyRequired = isRequestBodyRequired;
    }

    public String url() {
        return url;
    }

    public boolean isRequestBodyRequired() {
        return isRequestBodyRequired;
    }

    /**
     * Get the URL hash value for the enum.
     *
     * @return The URL hash value.
     */
    public String urlCode() {
        /*
         * Burayi degistirmeyin, cunku vt kaydi sirasinda onemli
         * aksi takdirde vt kaydi degisebilir ve var olan kayitlarin
         * ulasilamaz hale gelmesine sebep olabilir.
         */
        return url;
    }

    /**
     * Get all enums that do not require a request body.
     *
     * @return List of enums that do not require a request body.
     */
    public static List<DataSourceEnum> getNoRequestBodyEnums() {
        return Arrays.stream(DataSourceEnum.values())
                .filter(e -> !e.isRequestBodyRequired())
                .toList();
    }

    /**
     * Get all enums that require a request body.
     *
     * @return List of enums that require a request body.
     */
    public static List<DataSourceEnum> getRequestWithBodyEnums() {
        return Arrays.stream(DataSourceEnum.values())
                .filter(DataSourceEnum::isRequestBodyRequired)
                .toList();
    }

    /**
     * Get all enums for case details requests.
     * 
     * @return List of enums for case details requests.
     */
    public static List<DataSourceEnum> getRequestsForCaseDetailsEnums() {
        return Arrays.asList(UYAP_CASE_HISTORY, UYAP_CASE_TAHSILAT_REDDIYAT, UYAP_CASE_TARAFLAR);
    }

}
