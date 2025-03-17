package com.hukapp.service.auth.modules.app.repository;

import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeDataCaseDetails;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<NoSqlLikeDataCaseDetails, Long> {

    Optional<NoSqlLikeDataCaseDetails> findByEmailAndDataSourceAndCaseNumber(String email, DataSourceEnum dataSource, String caseNumber);

}
