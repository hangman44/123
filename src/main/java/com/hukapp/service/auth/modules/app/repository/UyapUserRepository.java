package com.hukapp.service.auth.modules.app.repository;

import com.hukapp.service.auth.modules.sync.entity.NoSqlLikeData;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UyapUserRepository extends JpaRepository<NoSqlLikeData, Long> {
    
    // Query method to find a record by email and dataSource
    Optional<NoSqlLikeData> findByEmailAndDataSource(String email, DataSourceEnum dataSource);
    
}
