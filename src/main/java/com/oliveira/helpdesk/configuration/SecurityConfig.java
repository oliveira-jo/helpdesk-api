package com.oliveira.helpdesk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.oliveira.helpdesk.security.JwtTokenFilter;
import com.oliveira.helpdesk.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsServiceImpl userDetailsService;

  private final JwtTokenFilter jwtTokentFilter;

  private static final String[] SWAGGET_WHITELIST = { "/swagger-ui/**", "/v3/api-docs/**" };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // .sessionManagement(session ->
        // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers(SWAGGET_WHITELIST).permitAll()
            .requestMatchers("/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/users/registerSupportAttendent").hasRole("ADMIN")
            .anyRequest().authenticated())
        .formLogin(withDefaults -> withDefaults.disable())
        .logout(withDefaults -> withDefaults.disable())
        .cors(withDefaults -> withDefaults.disable())
        .csrf(withDefaults -> withDefaults.disable());

    http.addFilterBefore(jwtTokentFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  public void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());

  }

}
