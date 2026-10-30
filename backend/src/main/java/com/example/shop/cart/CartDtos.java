package com.example.shop.cart;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;
public final class CartDtos {
 private CartDtos(){}
 public record Quantity(@Min(1) int quantity){}
 public record AddItem(@jakarta.validation.constraints.NotNull Long productId,@Min(1) int quantity){}
 public record Item(Long productId,String name,String categoryName,String imageUrl,BigDecimal unitPrice,int quantity,BigDecimal lineTotal,int stockQuantity){}
 public record View(List<Item> items,int itemCount,BigDecimal subtotal){}
}
