package com.hukapp.service.auth.modules.app.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hukapp.service.auth.common.exception.custom.ResourceNotFoundException;
import com.hukapp.service.auth.modules.app.repository.CaseRepository;
import com.hukapp.service.auth.modules.app.service.UyapCaseService;
import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeDataCaseDetails;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class UyapCaseServiceImpl implements UyapCaseService {

    private final CaseRepository caseRepository;

    @Override
    public String getCaseInfo(String subject, DataSourceEnum dataSource, String caseNumber) {
        Optional<NoSqlLikeDataCaseDetails> data = caseRepository.findByEmailAndDataSourceAndCaseNumber(subject,
                dataSource, caseNumber);
        if (data.isPresent()) {
            return data.get().getJsonData();
        } else {
            throw new ResourceNotFoundException("Case info not found for user: " + subject
                    + " and case number: " + caseNumber);
        }
    }
}
