package com.yang.auth.utils;

import com.yang.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token分为了三部分，头部(header), 荷载(Payload)和签名(Signature),每部分用.分隔
 * 其中头部和荷载部分使用了Base64编码，分别解码之后得到两个JSON串
 * 第一部分-头部
 * {
 *   "alg": "HS256",
 *   "typ": "JWT"
 * }
 * alg字段为加密算法，这是告诉我们 HMAC 采用 HS512 算法对 JWT 进行的签名
 *
 *  第二部分-荷载
 *  {
 *   "sub": "1234567890",
 *   "name": "John Doe",
 *   "iat": 1516239022
 * }
 * 荷载的字段和含义：
 * iss: 该JWT的签发者
 * sub: 该JWT所面向的用户
 * aud: 接收该JWT的一方
 * exp(expires): 什么时候过期，这里是一个Unix时间戳
 * iat(issued at): 在什么时候签发的
 * 这段告诉我们这个Token中含有的数据声明（Claim），这个例子里面有三个声明：sub, name 和 iat。在我们这个例子中，分别代表着
 * 所面向的用户、用户名、创建时间，当然你可以把任意数据声明在这里
 *
 * 第三部分-签名
 * 第三部分签名则不能使用base64解码出来，该部分用于验证头部和荷载数据的完整性
 *
 */
@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtTokenUtil implements Serializable {
    // 为JWT基础信息加密和解密的密匙
    private String secret;
    // JWT令牌的有效期
    private Long expiration;
    // 携带JWT令牌的HTTP的Header的名称
    private String header;

    /**
     * 生成Token令牌
     *
     * @param userDetails 用户
     * @return token令牌
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", userDetails.getUsername());
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 从claims生成令牌，如果看不懂就看谁调用它
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder()
                .setClaims(claims)  //自定义属性 放入用户拥有请求权限
                .setExpiration(expirationDate)  //失效时间
                .signWith(SignatureAlgorithm.HS512, secret) //签名算法和密钥
                .compact();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从令牌中获取数据声明，如果看不懂就看谁调用它
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 旧令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
}
