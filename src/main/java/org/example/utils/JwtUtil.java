package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类：签发 / 校验 token，逻辑同 JwtTest。
 * 全是静态方法，直接 JwtUtil.generateJwt(...) 调用。
 *
 * 静态字段没法用 @Value 注入，所以由 Spring 创建本类实例时，
 * 在构造器里把 application.yaml 的 jwt.secret、jwt.expiration-hours 灌进静态字段。
 */
@Component
public class JwtUtil {

    /** 签名密钥：HS256 要求 >= 256 位（32 字节），不足会抛 WeakKeyException */
    private static SecretKey key;

    /** token 有效期，默认 12 小时 */
    private static Duration expiration;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration-hours:12}") long expirationHours) {
        JwtUtil.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        JwtUtil.expiration = Duration.ofHours(expirationHours);
    }

    /** 签发 token：claims 是业务数据（如 id、username），返回 header.payload.signature 字符串 */
    public static String generateJwt(Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(claims)                              // 放进载荷
                .issuedAt(Date.from(now))                    // iat 签发时间
                .expiration(Date.from(now.plus(expiration))) // exp 过期时间
                .signWith(key, Jwts.SIG.HS256)               // HMAC-SHA256 签名
                .compact();                                  // 生成 token 字符串
    }

    /** 校验 token 并取回载荷：签名不对抛 SignatureException，过期抛 ExpiredJwtException */
    public static Claims parseJwt(String jwt) {
        return Jwts.parser()
                .verifyWith(key)         // 用同一把密钥验签
                .build()
                .parseSignedClaims(jwt)  // 校验签名和 exp/nbf
                .getPayload();           // 取出 Claims
    }
}
