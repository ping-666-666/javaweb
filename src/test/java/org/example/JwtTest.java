package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtTest {

    /** 签名密钥：HS256 要求 >= 256 位（32 字节），旧的 "itheima" 会抛 WeakKeyException；真实项目放配置文件里 */
    private static final SecretKey KEY = Keys.hmacShaKeyFor(
            "tilas-web-management-jwt-secret-key-256bit".getBytes(StandardCharsets.UTF_8));

    @Test
    public void testGenJwt() {
        // 1. 自定义载荷：要放进 token 的业务数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 10);
        claims.put("username", "itheima");

        // 2. 基准时间：下面签发时间和过期时间都以它为准
        Instant now = Instant.now();

        // 3. 组装 + 签名，最后 compact() 得到 header.payload.signature 三段式字符串
        String jwt = Jwts.builder()
                .claims(claims)                                        // 放进载荷（旧：addClaims(claims)）
                .issuedAt(Date.from(now))                              // iat 签发时间（旧：setIssuedAt(new Date())）
                .expiration(Date.from(now.plus(Duration.ofHours(12)))) // exp 过期时间，12 小时后（旧：new Date(System.currentTimeMillis() + 12*60*60*1000)）
                .signWith(KEY, Jwts.SIG.HS256)                         // 用 HMAC-SHA256 签名（旧：signWith(SignatureAlgorithm.HS256, "itheima")）
                .compact();                                            // 生成 token 字符串

        // 4. 打印结果，形如 eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAs...
        System.out.println(jwt);

        // 5. 验签并取回载荷：签名不对抛 SignatureException，过期抛 ExpiredJwtException
        Claims payload = Jwts.parser()                                 // 解析器（旧：parserBuilder()）
                .verifyWith(KEY)                                       // 用同一把密钥验签（旧：setSigningKey("itheima")）
                .build()
                .parseSignedClaims(jwt)                                // 校验签名和 exp/nbf（旧：parseClaimsJws(jwt)）
                .getPayload();                                         // 取出 Claims（旧：getBody()）

        System.out.println(payload);
        // 6. 断言：取回来的数据就是刚才写进去的
        assertEquals(10, payload.get("id", Integer.class).intValue());
        assertEquals("itheima", payload.get("username", String.class));
    }
}
