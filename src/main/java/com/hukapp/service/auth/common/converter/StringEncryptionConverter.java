package com.hukapp.service.auth.common.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hukapp.service.auth.common.util.EncryptionUtil;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
@Component
public class StringEncryptionConverter implements AttributeConverter<String, String> {

    private static EncryptionUtil encryptionUtil;

    @Autowired
    public void setEncryptionUtil(EncryptionUtil encryptionUtil) {
        StringEncryptionConverter.encryptionUtil = encryptionUtil;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encryptionUtil.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return encryptionUtil.decrypt(dbData);
    }
}