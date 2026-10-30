package com.example.shop.admin;
import com.example.shop.account.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/admin") public class AdminDashboardController {
 private final AdminDashboardService service;public AdminDashboardController(AdminDashboardService service){this.service=service;}
 @GetMapping("/dashboard") public AdminDashboardService.Stats stats(@AuthenticationPrincipal UserAccount user){AdminAuthorization.require(user);return service.stats();}
}
