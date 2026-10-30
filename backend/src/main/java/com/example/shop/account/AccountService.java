package com.example.shop.account;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@Service @Transactional public class AccountService {
 private final UserRepository users;private final RoleRepository roles;private final PasswordEncoder encoder;private final JwtService jwt;
 public AccountService(UserRepository users,RoleRepository roles,PasswordEncoder encoder,JwtService jwt){this.users=users;this.roles=roles;this.encoder=encoder;this.jwt=jwt;}
 public AccountDtos.AuthView register(AccountDtos.Register d){if(users.findByEmailIgnoreCase(d.email()).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is already registered");UserAccount u=new UserAccount();u.email=d.email().trim().toLowerCase(Locale.ROOT);u.fullName=d.fullName().trim();u.passwordHash=encoder.encode(d.password());u.roles.add(role("ROLE_USER"));u=users.save(u);return new AccountDtos.AuthView(jwt.issue(u),view(u));}
 public AccountDtos.AuthView login(AccountDtos.Login d){UserAccount u=users.findByEmailIgnoreCase(d.email()).filter(x->encoder.matches(d.password(),x.passwordHash)).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password"));return new AccountDtos.AuthView(jwt.issue(u),view(u));}
 public AccountDtos.UserView me(UserAccount user){return view(user);}
 public AccountDtos.UserView update(UserAccount user,AccountDtos.Profile d){user.fullName=d.fullName().trim();return view(user);}
 private Role role(String name){return roles.findByName(name).orElseThrow();}
 private AccountDtos.UserView view(UserAccount u){return new AccountDtos.UserView(u.id,u.email,u.fullName,u.roles.stream().map(r->r.name).sorted().toList());}
}
