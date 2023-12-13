package my.todo.infrastructure.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
      .csrf((csrf)->
        csrf.disable())
      .headers((headers) ->
        headers.frameOptions((options) -> 
          options.sameOrigin()))
      .authorizeHttpRequests((authorizeHttpRequests) ->
        authorizeHttpRequests
          .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
          .requestMatchers(new AntPathRequestMatcher( "/home")).permitAll()
          .requestMatchers(new AntPathRequestMatcher( "/login")).permitAll()
          .requestMatchers(new AntPathRequestMatcher("/api/user/signup")).permitAll()
          .requestMatchers(new AntPathRequestMatcher("/api/user/signin")).permitAll()
          .anyRequest().authenticated())
      .formLogin((formLogin) ->
        formLogin
          .usernameParameter("username")
          .passwordParameter("password")
          .loginPage("/login")
          .permitAll())
        .logout((logout) -> 
          logout
            .permitAll());

    return http.build();
  }


  // @Bean
  // public UserDetailsService userDetailsService() {
  //   UserDetails user =
  //     User.user
  // }
  
  // public void 

}
