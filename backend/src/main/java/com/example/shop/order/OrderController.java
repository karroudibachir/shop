package com.example.shop.order;
import com.example.shop.account.UserAccount;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.shop.account.AdminAuthorization;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api") public class OrderController {
 private final OrderService service;public OrderController(OrderService service){this.service=service;}
 @PostMapping("/orders") public OrderDtos.View checkout(@AuthenticationPrincipal UserAccount user,@Valid @RequestBody OrderDtos.Checkout body){return service.checkout(user,body);}
 @GetMapping("/orders") public Page<OrderDtos.View> list(@AuthenticationPrincipal UserAccount user,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="10") int size){return service.list(user,page,size);}
 @GetMapping("/orders/{id}") public OrderDtos.View get(@AuthenticationPrincipal UserAccount user,@PathVariable long id){return service.get(user,id,AdminAuthorization.isAdmin(user));}
 @PostMapping("/orders/{id}/cancel") public OrderDtos.View cancel(@AuthenticationPrincipal UserAccount user,@PathVariable long id){return service.cancel(user,id);}
 @PostMapping("/orders/{id}/pay") public OrderDtos.View pay(@AuthenticationPrincipal UserAccount user,@PathVariable long id){return service.pay(user,id);}
 @GetMapping("/admin/orders") public Page<OrderDtos.View> all(@AuthenticationPrincipal UserAccount user,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="25") int size){AdminAuthorization.require(user);return service.all(page,size);}
 @PatchMapping("/admin/orders/{id}/status") public OrderDtos.View status(@AuthenticationPrincipal UserAccount user,@PathVariable long id,@Valid @RequestBody OrderDtos.StatusUpdate body){AdminAuthorization.require(user);return service.status(id,body.status());}
}
