package com.hukapp.service.auth.modules.sync.entity;

import com.hukapp.service.auth.common.converter.StringEncryptionConverter;
import com.hukapp.service.auth.common.entity.BaseEntity;
import com.hukapp.service.auth.modules.sync.enums.DataSourceEnum;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "no_sql_like_data_case_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class NoSqlLikeDataCaseDetails extends BaseEntity {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DataSourceEnum dataSource;

    @Column(nullable = false)
    private String caseNumber;

    @Basic(fetch = FetchType.LAZY)
    @Convert(converter = StringEncryptionConverter.class)
    @Column(columnDefinition = "text")
    private String jsonData;

    @Column(nullable = false)
    private boolean isClosedCase;
    
}
