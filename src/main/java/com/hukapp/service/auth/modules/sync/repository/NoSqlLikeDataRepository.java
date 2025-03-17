package com.hukapp.service.auth.modules.sync.repository;

import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeData;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoSqlLikeDataRepository extends JpaRepository<NoSqlLikeData, Long> {
    
    Optional<List<NoSqlLikeData>> findByEmail(String email);
    
    // Query method to find a record by email and dataSource
    Optional<NoSqlLikeData> findByEmailAndDataSource(String email, DataSourceEnum dataSource);
    
    // Saves or updates a record:
    // If a record exists with the same email and dataSource, update its jsonData field.
    // Otherwise, save the new entity.
    default NoSqlLikeData saveOrUpdate(NoSqlLikeData entity) {
        Optional<NoSqlLikeData> existingRecord = findByEmailAndDataSource(entity.getEmail(), entity.getDataSource());
        if (existingRecord.isPresent()) {
            NoSqlLikeData updatedEntity = existingRecord.get();
            updatedEntity.setJsonData(entity.getJsonData());
            return save(updatedEntity);
        } else {
            return save(entity);
        }
    }
}
