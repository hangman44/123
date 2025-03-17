package com.hukapp.service.auth.common.util;

import org.springframework.stereotype.Component;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import kong.unirest.core.HttpRequestWithBody;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UyapUtil {

    private UyapUtil() {
        // private constructor to hide the implicit public one
    }

    /**
     * Create a UYAP HTTP POST request. Add request body if needed.
     * Adds necessary headers for UYAP requests to be compatible with the UYAP
     * system.
     * 
     * @param jsid    String JSESSIONID
     * @param uyapUrl String UYAP URL
     * @return HttpRequestWithBody: kong.unirest.core.HttpRequestWithBody
     */
    public static HttpRequestWithBody createUyapHttpPostRequest(String jsid, String uyapUrl) {

        return Unirest.post(uyapUrl)
                .header("Cookie", "JSESSIONID=" + jsid)
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Encoding", "gzip, deflate, br, zstd")
                .header("Accept-Language", "tr-TR,tr;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Cache-Control", "no-cache")
                .header("Content-Type", "application/json")
                .header("Expires", "0")
                .header("Origin", "https://avukatbeta.uyap.gov.tr")
                .header("Pragma", "no-cache")
                .header("Referer", "https://avukatbeta.uyap.gov.tr")
                .header("sec-ch-ua", "\"Not(A:Brand\";v=\"99\", \"Google Chrome\";v=\"133\", \"Chromium\";v=\"133\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-origin")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36");

    }

    /**
     * Do not forget to override the toString method of the request object since it
     * is used in the callUyap method. If the toString method is not
     * overridden, the default toString method of the Object class will be used,
     * which will not return the expected result.
     * 
     * @return JsonNode or null if the response is not successful.
     */
    public static <T> HttpResponse<JsonNode> callUyap(String subject, String jsid, DataSourceEnum dataSource, T request) {

        log.debug("Calling UYAP for {} for user '{}'", dataSource, subject);

        HttpResponse<JsonNode> response = UyapUtil
                .createUyapHttpPostRequest(jsid, dataSource.url())
                .body(request != null ? request.toString() : "")
                .asJson();

        if (response.isSuccess()) {
            log.debug("Calling UYAP for {} for user '{}' successfull. Response: {}", dataSource, subject, response.getBody());
        } else {
            log.error(
                    "Unsuccesfull UYAP response. " +
                            "Sync is failed for {} for user '{}'. Request body string: {}. " +
                            "Check whether you have overridden the toString() method of the request object. UYAP Response: {}",
                    dataSource,
                    subject, request, response.getBody());
        }
        return response;
    }

}
