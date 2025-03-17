package com.hukapp.service.auth.modules.sync.service.impl;

import com.hukapp.service.auth.modules.sync.repository.NoSqlLikeDataCaseDetailsRepository;
import com.hukapp.service.auth.modules.sync.repository.NoSqlLikeDataRepository;
import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeData;
import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeDataCaseDetails;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;
import com.hukapp.service.auth.modules.sync.service.NoSqlLikeDataService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoSqlLikeDataServiceImpl implements NoSqlLikeDataService {

    private final NoSqlLikeDataRepository repository;
    private final NoSqlLikeDataCaseDetailsRepository caseDetailsRepository;

    public List<NoSqlLikeData> findAll() {
        return repository.findAll();
    }

    public Optional<NoSqlLikeData> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<NoSqlLikeData> findByEmailAndDataSource(String email, DataSourceEnum dataSource) {
        return repository.findByEmailAndDataSource(email,dataSource);
    }

    public NoSqlLikeData save(NoSqlLikeData data) {
        return repository.save(data);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public NoSqlLikeData saveOrUpdate(NoSqlLikeData data) {
        return repository.saveOrUpdate(data);
    }

    @Override
    public NoSqlLikeDataCaseDetails saveOrUpdate(NoSqlLikeDataCaseDetails dataCaseDetails) {
        return caseDetailsRepository.saveOrUpdate(dataCaseDetails);
    }
}
