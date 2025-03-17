package com.hukapp.service.auth.modules.sync.service;

import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeData;
import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeDataCaseDetails;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import java.util.List;
import java.util.Optional;

public interface NoSqlLikeDataService {

    List<NoSqlLikeData> findAll();

    Optional<NoSqlLikeData> findById(Long id);

    Optional<NoSqlLikeData> findByEmailAndDataSource(String email, DataSourceEnum dataSource);

    NoSqlLikeData save(NoSqlLikeData data);

    void deleteById(Long id);

    NoSqlLikeData saveOrUpdate(NoSqlLikeData data);

    NoSqlLikeDataCaseDetails saveOrUpdate(NoSqlLikeDataCaseDetails dataCaseDetails);
}
