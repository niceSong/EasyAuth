package com.tyytogether.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.tyytogether.properties.CertificationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class EasyJWT {
    public static final String KEY_PAYLOAD = "payload";

    @Autowired
    private CertificationProperties certificationProperties;

    public String sign(String payload, long expire) {
        return JWT.create().withClaim(KEY_PAYLOAD, payload)
                .withIssuer(this.certificationProperties.getIssuer()).withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plus(expire, ChronoUnit.SECONDS))).sign(Algorithm.HMAC256(certificationProperties.getSecret()));
    }

    public String sign(String payload) {
        return this.sign(payload, this.certificationProperties.getExpire());
    }
}
