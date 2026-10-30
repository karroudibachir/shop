package com.example.shop.account;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
public final class AdminAuthorization {
 private AdminAuthorization(){}
 public static void require(UserAccount user){if(user==null)throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication required");if(user.roles.stream().noneMatch(role->"ROLE_ADMIN".equals(role.name)))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Administrator role required");}
 public static boolean isAdmin(UserAccount user){return user!=null&&user.roles.stream().anyMatch(role->"ROLE_ADMIN".equals(role.name));}
}
