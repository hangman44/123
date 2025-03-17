package com.hukapp.service.auth.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for RSA public and private keys.
 * 
 *  To generate a keypair, use the following commands:
 * - openssl genrsa -out keypair.pem 2048
 * - openssl rsa -in keypair.pem -pubout -out public.pem
 * - openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
 */
@ConfigurationProperties("rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
    public RsaKeyProperties {
        if (publicKey == null || privateKey == null) {
            throw new IllegalArgumentException("Public and private keys must not be null");
        }
    }
}
