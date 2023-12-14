package my.todo.infrastructure.security.jwt;

import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.todo.infrastructure.configure.JwtTokenConfig;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
  
  static final Long MINUTE = 60 * 60 * 1000L;

  private final JwtTokenConfig jwtTokenConfig;
  private final UserDetailsService userDetailsService;

  public String generateToken(String user, String role) {
    log.info("[generateToken] jwtTokenConfig={}", jwtTokenConfig);

    Claims claims = Jwts.claims().setSubject(user);
    claims.put("role", role);

    Date now = new Date();
    Date expiration = new Date(now.getTime() + (jwtTokenConfig.getExpiredTime() * MINUTE));

    log.info("[generateToken] date={} / {}", now, expiration);

    return Jwts.builder()
      .setClaims(claims)
      .setIssuer(jwtTokenConfig.getIssuer())
      .setIssuedAt(now)
      .setExpiration(expiration)
      .signWith(SignatureAlgorithm.HS256, jwtTokenConfig.getSecret())
      .compact();
  }

  public Authentication getAuthentication(String token) {
    String username = parseToken(token).getBody().getSubject();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return
      new UsernamePasswordAuthenticationToken(
        userDetails, "", userDetails.getAuthorities());
  }

  public boolean validate(String token) {
    try {
      Jws<Claims> claims = parseToken(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  public String resolveTokenFromHeader(ServletRequest resq) {
    HttpServletRequest request = (HttpServletRequest) resq;
    return request.getHeader(jwtTokenConfig.getHeader());
  }

  private Jws<Claims> parseToken(String token) {
    Jws<Claims> claims =
      Jwts.parser()
        .setSigningKey(jwtTokenConfig.getSecret())
        .parseClaimsJws(token);
    return claims;
  }

}
