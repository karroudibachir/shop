package com.example.shop.account;
import jakarta.validation.constraints.*;
import java.util.List;
public final class AccountDtos {
 private AccountDtos(){}
 public record Register(@NotBlank @Email @Size(max=190) String email,@NotBlank @Size(min=8,max=72) String password,@NotBlank @Size(max=140) String fullName){}
 public record Login(@NotBlank @Email String email,@NotBlank String password){}
 public record Profile(@NotBlank @Size(max=140) String fullName){}
 public record UserView(Long id,String email,String fullName,List<String> roles){}
 public record AuthView(String token,UserView user){}
}
