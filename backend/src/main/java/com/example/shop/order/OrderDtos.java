package com.example.shop.order;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public final class OrderDtos {
 private OrderDtos(){}
 public record Checkout(@NotBlank @Size(max=140) String shippingName,@NotBlank @Size(max=300) String shippingAddress,@NotBlank @Size(max=100) String shippingCity,@NotBlank @Size(max=30) String shippingPostalCode,@NotBlank @Size(max=100) String shippingCountry){}
 public record StatusUpdate(@NotNull ShopOrder.Status status){}
 public record Item(Long productId,String name,String categoryName,BigDecimal unitPrice,int quantity,BigDecimal lineTotal){}
 public record View(Long id,ShopOrder.Status status,String shippingName,String shippingAddress,String shippingCity,String shippingPostalCode,String shippingCountry,BigDecimal total,Instant createdAt,List<Item> items){}
}
