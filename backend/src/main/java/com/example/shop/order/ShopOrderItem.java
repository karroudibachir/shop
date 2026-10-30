package com.example.shop.order;
import com.example.shop.product.Product;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="order_items") public class ShopOrderItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="order_id",nullable=false) public ShopOrder order;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="product_id",nullable=false) public Product product;
 @Column(name="product_name",nullable=false,length=160) public String productName;
 @Column(name="unit_price",nullable=false,precision=10,scale=2) public BigDecimal unitPrice;
 @Column(nullable=false) public int quantity;
 protected ShopOrderItem(){}
 public ShopOrderItem(ShopOrder o,Product p,int q){order=o;product=p;productName=p.getName();unitPrice=p.getPrice();quantity=q;}
}
