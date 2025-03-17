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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "no_sql_like_data", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "dataSource"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoSqlLikeData extends BaseEntity {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DataSourceEnum dataSource;

    @Basic(fetch = FetchType.LAZY)
    @Convert(converter = StringEncryptionConverter.class)
    @Column(columnDefinition = "text")
    private String jsonData;
    
}
