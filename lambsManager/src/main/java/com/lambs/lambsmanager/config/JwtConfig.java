package com.lambs.lambsmanager.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 配置JWT令牌
 * ConfigurationProperties 配置属性,前缀和忽略无效文件
 */
@ConfigurationProperties(prefix = "config.jwt", ignoreInvalidFields = true)
@Component
@Data
public class JwtConfig {

    private String secret;
    private Long timeout;
    private String header;

    /**
     * 传入要生成token的字段,加密字段
     *
     * @param subject
     * @return
     */
    public String createToken(String subject) {
        //发布时间
        Date nowdate = new Date();
        //过期时间,等于获取当前时间戳+设置的秒数*1000转换为毫秒
        Date expireDate = new Date(nowdate.getTime() + timeout * 1000);
        return Jwts.builder()
                //设置令牌类型
                .setHeaderParam("typ", "JWT")
                //存入负载中
                .setSubject(subject)
                //设置发布时间
                .setIssuedAt(nowdate)
                //设置过期时间
                .setExpiration(expireDate)
                //设置加密算法
                .signWith(SignatureAlgorithm.HS512, secret)
                //生成方法调用
                .compact();
    }

    /**
     * 传入token并进行解析
     *
     * @param token
     * @return
     */
    public Claims getTokenClaim(String token) {
        //解析,设置签名,解析要求的token
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 从token中获取传入的字段
     *
     * @param token
     * @return
     */
    public String getSubject(String token) {
        return getTokenClaim(token).getSubject();
    }

}
