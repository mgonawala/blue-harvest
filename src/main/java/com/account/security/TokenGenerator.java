package com.account.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.secret.timeout}")
  private int timeoutInMs;

  public String generateToken(Authentication authentication) {

    SecureUser userPrincipal = (SecureUser) authentication.getPrincipal();

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + timeoutInMs);

    return Jwts.builder()
        .setSubject(Long.toString(userPrincipal.getId()))
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public Long getUserIdFromJWT(String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    return Long.parseLong(claims.getSubject());
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException ex) {
      System.out.println("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      System.out.println("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      System.out.println("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      System.out.println("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      System.out.println("JWT claims string is empty.");
    }
    return false;
  }
}
