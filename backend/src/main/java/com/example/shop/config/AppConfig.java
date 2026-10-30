package com.example.shop.config;
import com.example.shop.account.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration public class AppConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean CommandLineRunner seedAdmin(UserRepository users,RoleRepository roles,PasswordEncoder encoder,@Value("${ADMIN_EMAIL:}") String email,@Value("${ADMIN_PASSWORD:}") String password){return args->{if(email.isBlank()||password.isBlank())return;Role role=roles.findByName("ROLE_ADMIN").orElseThrow();if(users.findByEmailIgnoreCase(email).isEmpty()){UserAccount u=new UserAccount();u.email=email.toLowerCase();u.fullName="Shop administrator";u.passwordHash=encoder.encode(password);u.roles.add(role);users.save(u);}};}
}
