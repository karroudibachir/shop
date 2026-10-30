package com.example.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import com.example.shop.account.JwtFilter;

@Configuration @EnableWebSecurity @EnableMethodSecurity
public class SecurityConfig {
 @Bean FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter) {
  FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>(jwtFilter);
  registration.setEnabled(false);
  return registration;
 }

 @Bean SecurityFilterChain security(HttpSecurity http,JwtFilter jwtFilter) throws Exception {
  return http.csrf(csrf->csrf.disable()).cors(cors->{}).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/api/products/**","/api/categories/**","/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html","/actuator/health","/actuator/info").permitAll().anyRequest().authenticated()).exceptionHandling(e->e.authenticationEntryPoint((req,res,ex)->res.sendError(401)).accessDeniedHandler((req,res,ex)->res.sendError(403))).addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class).build();
 }
}
