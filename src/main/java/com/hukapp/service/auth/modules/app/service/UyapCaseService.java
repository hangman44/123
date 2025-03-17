package com.hukapp.service.auth.modules.app.service;

import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

public interface UyapCaseService {

    String getCaseInfo(String subject, DataSourceEnum dataSource, String caseNumber);

}
