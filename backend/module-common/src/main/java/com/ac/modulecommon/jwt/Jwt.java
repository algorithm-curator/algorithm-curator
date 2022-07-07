package com.ac.modulecommon.jwt;

import com.ac.modulecommon.entity.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;

/**
 * JWT 발행 클래스
 */
@Getter
public class Jwt {

    private final String issuer;

    private final String clientSecret;

    private final int expirySeconds;

    private final Algorithm algorithm;

    private final JWTVerifier jwtVerifier;

    public Jwt(String issuer, String clientSecret, int expirySeconds) {
        this.issuer = issuer;
        this.clientSecret = clientSecret;
        this.expirySeconds = expirySeconds;
        this.algorithm = Algorithm.HMAC512(clientSecret);
        this.jwtVerifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    public String createApiToken(User user, String[] roles) {
        Claims claims = Claims.of(user, roles);
        return createNewToken(claims);
    }

    public String createNewToken(Claims claims) {
        Date now = new Date();
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        builder.withIssuer(issuer);
        builder.withIssuedAt(now);

        if (expirySeconds > 0) {
            builder.withExpiresAt(new Date(now.getTime() + expirySeconds * 1_000L));
        }

        builder.withClaim("id", claims.id);
        builder.withClaim("oauthId", claims.oauthId);
        builder.withClaim("nickname", claims.nickname);
        builder.withClaim("profileImage", claims.profileImage);
        builder.withArrayClaim("roles", claims.roles);
        return builder.sign(algorithm);
    }

    public String createRefreshToken(String token) throws JWTVerificationException {
        Claims claims = verify(token);
        claims.eraseIat();
        claims.eraseExp();
        return createNewToken(claims);
    }

    public Claims verify(String token) throws JWTVerificationException  {
        return new Claims(jwtVerifier.verify(token));
    }

    @ToString
    @Builder
    @AllArgsConstructor
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Claims {
        private Long id;
        private Long oauthId;
        private String nickname;
        private String profileImage;
        private String[] roles;
        private Date iat;
        private Date exp;

        private Claims(DecodedJWT decodedJWT) {
            Claim id = decodedJWT.getClaim("id");
            if (!id.isNull())
                this.id = id.asLong();

            Claim oauthId = decodedJWT.getClaim("oauthId");
            if (!oauthId.isNull())
                this.oauthId = oauthId.asLong();

            Claim nickname = decodedJWT.getClaim("nickname");
            if (!nickname.isNull())
                this.nickname = nickname.asString();

            Claim profileImage = decodedJWT.getClaim("profileImage");
            if (!profileImage.isNull())
                this.profileImage = profileImage.asString();

            Claim roles = decodedJWT.getClaim("roles");
            if (!roles.isNull())
                this.roles = roles.asArray(String.class);
            this.iat = decodedJWT.getIssuedAt();
            this.exp = decodedJWT.getExpiresAt();
        }

        public static Claims of(User user, String[] roles) {
            Claims claims = new Claims();
            claims.id = user.getId();
            claims.oauthId = user.getOauthId();
            claims.nickname = user.getNickname();
            claims.roles = roles;
            return claims;
        }

        public long iat() {
            return ObjectUtils.isNotEmpty(iat) ? iat.getTime() : -1;
        }

        public long exp() {
            return ObjectUtils.isNotEmpty(exp) ? exp.getTime() : -1;
        }

        public void eraseIat() {
            iat = null;
        }

        public void eraseExp() {
            exp = null;
        }
    }
}
