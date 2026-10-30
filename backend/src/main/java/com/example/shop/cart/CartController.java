package com.example.shop.cart;
import com.example.shop.account.UserAccount;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/cart") public class CartController {
 private final CartService service;public CartController(CartService service){this.service=service;}
 @GetMapping public CartDtos.View get(@AuthenticationPrincipal UserAccount user){return service.get(user);}
 @PostMapping("/items") public CartDtos.View add(@AuthenticationPrincipal UserAccount user,@Valid @RequestBody CartDtos.AddItem body){return service.add(user,body);}
 @PatchMapping("/items/{productId}") public CartDtos.View set(@AuthenticationPrincipal UserAccount user,@PathVariable long productId,@Valid @RequestBody CartDtos.Quantity body){return service.set(user,productId,body);}
 @DeleteMapping("/items/{productId}") public CartDtos.View remove(@AuthenticationPrincipal UserAccount user,@PathVariable long productId){return service.remove(user,productId);}
 @DeleteMapping public CartDtos.View clear(@AuthenticationPrincipal UserAccount user){return service.clear(user);}
}
