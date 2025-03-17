package com.hukapp.service.auth.modules.sync.repository;

import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeDataCaseDetails;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoSqlLikeDataCaseDetailsRepository extends JpaRepository<NoSqlLikeDataCaseDetails, Long> {

    Optional<List<NoSqlLikeDataCaseDetails>> findByEmail(String email);

    // Query method to find a record by email and dataSource
    Optional<NoSqlLikeDataCaseDetails> findByEmailAndDataSource(String email, DataSourceEnum dataSource);

    Optional<NoSqlLikeDataCaseDetails> findByEmailAndDataSourceAndCaseNumber(String email, DataSourceEnum dataSource, String caseNumber);
    
    // Saves or updates a record:
    // If a record exists with the same email and dataSource, update its jsonData
    // field.
    // Otherwise, save the new entity.
    default NoSqlLikeDataCaseDetails saveOrUpdate(NoSqlLikeDataCaseDetails entity) {
        Optional<NoSqlLikeDataCaseDetails> existingRecord = findByEmailAndDataSourceAndCaseNumber(entity.getEmail(),
                entity.getDataSource(),entity.getCaseNumber());
        if (existingRecord.isPresent()) {
            NoSqlLikeDataCaseDetails updatedEntity = existingRecord.get();
            updatedEntity.setJsonData(entity.getJsonData());
            return save(updatedEntity);
        } else {
            return save(entity);
        }
    }
}
