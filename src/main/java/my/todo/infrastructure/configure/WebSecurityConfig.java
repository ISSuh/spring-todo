package my.todo.infrastructure.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
import my.todo.infrastructure.security.jwt.JwtAuthenticationTokenFilter;
import my.todo.infrastructure.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter(jwtTokenProvider);
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
      .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
    .httpBasic((httpBasic) ->
      httpBasic.disable())
    .csrf((csrf)->
      csrf.disable())
    .sessionManagement((sessionManagement) ->
      sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .headers((headers) ->
      headers.frameOptions((options) -> 
        options.sameOrigin()))
    .authorizeHttpRequests((authorizeHttpRequests) ->
      authorizeHttpRequests        
        .requestMatchers(new AntPathRequestMatcher("/api/sign/**")).permitAll()
        .requestMatchers(new AntPathRequestMatcher("/api/todo/**")).hasAnyRole("USER", "ADMIN")
        .anyRequest().authenticated()
    )
    .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
