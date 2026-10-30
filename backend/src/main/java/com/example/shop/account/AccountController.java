package com.example.shop.account;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api") public class AccountController {
 private final AccountService service;public AccountController(AccountService service){this.service=service;}
 @PostMapping("/auth/register") public AccountDtos.AuthView register(@Valid @RequestBody AccountDtos.Register body){return service.register(body);}
 @PostMapping("/auth/login") public AccountDtos.AuthView login(@Valid @RequestBody AccountDtos.Login body){return service.login(body);}
 @GetMapping("/users/me") public AccountDtos.UserView me(@AuthenticationPrincipal UserAccount user){return service.me(user);}
 @PutMapping("/users/me") public AccountDtos.UserView update(@AuthenticationPrincipal UserAccount user,@Valid @RequestBody AccountDtos.Profile body){return service.update(user,body);}
}
