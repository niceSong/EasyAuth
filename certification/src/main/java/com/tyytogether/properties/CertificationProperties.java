package com.tyytogether.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "easy.auth")
@Component
public class CertificationProperties {
    String secret = "easy-com.tyytogether.jwt";
    String issuer = "easy-com.tyytogether.jwt";
    String headerTokenKey;
    long expire = 259200L;

    public CertificationProperties() {
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public long getExpire() {
        return this.expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getHeaderTokenKey() {
        return headerTokenKey;
    }

    public void setHeaderTokenKey(String headerTokenKey) {
        this.headerTokenKey = headerTokenKey;
    }
}
