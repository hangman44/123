package com.hukapp.service.auth.modules.sync.service;

import com.hukapp.service.auth.common.dto.response.BaseResponse;

public interface UyapService {

    BaseResponse syncWithUyap(String subject, String jsid);

}
