package com.example.shop.product;

import static com.example.shop.product.ProductDtos.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.shop.account.UserAccount;
import com.example.shop.account.AdminAuthorization;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController @RequestMapping("/api")
public class ProductController {
 private final ProductService service; private final CategoryService categories;
 public ProductController(ProductService service,CategoryService categories){this.service=service;this.categories=categories;}
 @GetMapping("/products") public Page<ProductView> list(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="12") int size,@RequestParam(defaultValue="newest") String sort,@RequestParam(required=false) String q,@RequestParam(required=false) Long categoryId,@RequestParam(required=false) BigDecimal minPrice,@RequestParam(required=false) BigDecimal maxPrice){return service.list(page,size,sort,q,categoryId,minPrice,maxPrice);}
 @GetMapping("/products/{id}") public ProductView get(@PathVariable long id){return service.get(id);}
 @PostMapping("/products") public ProductView create(@AuthenticationPrincipal UserAccount user,@Valid @RequestBody ProductRequest body){AdminAuthorization.require(user);return service.create(body);}
 @PutMapping("/products/{id}") public ProductView update(@AuthenticationPrincipal UserAccount user,@PathVariable long id,@Valid @RequestBody ProductRequest body){AdminAuthorization.require(user);return service.update(id,body);}
 @DeleteMapping("/products/{id}") public void delete(@AuthenticationPrincipal UserAccount user,@PathVariable long id){AdminAuthorization.require(user);service.delete(id);}
 @GetMapping("/categories") public List<CategoryView> categories(){return categories.list();}
 @PostMapping("/categories") public CategoryView createCategory(@AuthenticationPrincipal UserAccount user,@Valid @RequestBody CategoryRequest body){AdminAuthorization.require(user);return categories.create(body);}
 @PutMapping("/categories/{id}") public CategoryView updateCategory(@AuthenticationPrincipal UserAccount user,@PathVariable long id,@Valid @RequestBody CategoryRequest body){AdminAuthorization.require(user);return categories.update(id,body);}
 @DeleteMapping("/categories/{id}") public void deleteCategory(@AuthenticationPrincipal UserAccount user,@PathVariable long id){AdminAuthorization.require(user);categories.delete(id);}
}
