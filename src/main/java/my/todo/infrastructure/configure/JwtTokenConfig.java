package my.todo.infrastructure.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt.token")
public class JwtTokenConfig {

  private String header;

  private String issuer;
  
  private String secret;

  private Long expiredTime;

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public Long getExpiredTime() {
    return expiredTime;
  }

  public void setExpiredTime(Long expiredTime) {
    this.expiredTime = expiredTime;
  }

  @Override
  public String toString() {
    return "JwtTokenConfig [header=" + header + ", issuer=" + issuer + ", secret=" + secret + ", expiredTime="
        + expiredTime + "]";
  }

}
