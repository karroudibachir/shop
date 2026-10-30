package com.example.shop.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

public final class ProductDtos {
 private ProductDtos() {}
 public record ProductView(Long id,String name,String description,BigDecimal price,int stockQuantity,String imageUrl,Long categoryId,String categoryName,boolean active,Instant createdAt) {
  static ProductView of(Product p){return new ProductView(p.id,p.name,p.description,p.price,p.stockQuantity,p.imageUrl,p.category==null?null:p.category.getId(),p.getCategoryName(),p.active,p.createdAt);}
 }
 public record ProductRequest(@NotBlank @Size(max=160) String name,@NotBlank String description,@NotNull @DecimalMin("0.01") BigDecimal price,@Min(0) int stockQuantity,@Size(max=500) String imageUrl,@NotNull Long categoryId) {}
 public record CategoryView(Long id,String name,String description) { static CategoryView of(Category c){return new CategoryView(c.id,c.name,c.description);} }
 public record CategoryRequest(@NotBlank @Size(max=80) String name,@Size(max=500) String description) {}
}
