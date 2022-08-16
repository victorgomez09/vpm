package com.vira.vpm.authservice.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vira.vpm.authservice.entity.AuthUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @PostConstruct
  protected void init() {
    jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
  }

  public String createToken(AuthUser authUser) {
    Map<String, Object> claims = new HashMap<>();
    claims = Jwts.claims().setSubject(authUser.getEmail());
    claims.put("id", authUser.getId());
    Date now = new Date();
    Date exp = new Date(now.getTime() + 3600000);
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(exp)
        .signWith(SignatureAlgorithm.HS256, jwtSecret)
        .compact();
  }

  public boolean validate(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getUserEmailFromToken(String token) {
    try {
      return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    } catch (Exception e) {
      return "bad token";
    }
  }
}
