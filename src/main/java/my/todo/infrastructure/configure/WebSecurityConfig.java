package my.todo.infrastructure.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  
//   .formLogin((formLogin) ->
//   formLogin
//       .usernameParameter("username")
//       .passwordParameter("password")
//       .loginPage("/authentication/login")
//       .failureUrl("/authentication/login?failed")
//       .loginProcessingUrl("/authentication/login/process")
// );

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
      .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
      .csrf((csrf)->
        csrf.disable())
      .headers((headers) ->
        headers.frameOptions((options) -> 
          options.sameOrigin()))
      .authorizeHttpRequests((authorizeHttpRequests) ->
        authorizeHttpRequests        
          .requestMatchers(new AntPathRequestMatcher("/api/sign/**")).permitAll()
          .requestMatchers(new AntPathRequestMatcher( "/home")).hasRole("USER")
          .requestMatchers(new AntPathRequestMatcher("/api/todo/**")).hasRole("USER")
          .anyRequest().authenticated())
      .formLogin((formLogin) ->
        formLogin
          .usernameParameter("username")
          .passwordParameter("password")
          .loginPage("/login")
          .defaultSuccessUrl("/home")
          .permitAll())
        .logout((logout) -> 
          logout
            .logoutSuccessUrl("/login")
            .permitAll());

    return http.build();
  }

}
