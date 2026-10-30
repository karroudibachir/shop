package com.example.shop.account;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component public class JwtFilter extends OncePerRequestFilter {
 private final JwtService jwt;private final UserRepository users;
 public JwtFilter(JwtService jwt,UserRepository users){this.jwt=jwt;this.users=users;}
 @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws ServletException,IOException{
  String header=request.getHeader("Authorization");if(header!=null&&header.startsWith("Bearer "))jwt.subject(header.substring(7)).flatMap(users::findByEmailIgnoreCase).ifPresent(user->{var authorities=user.roles.stream().map(r->new SimpleGrantedAuthority(r.name)).toList();SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,null,authorities));});chain.doFilter(request,response);
 }
}
