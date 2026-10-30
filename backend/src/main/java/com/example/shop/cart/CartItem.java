package com.example.shop.cart;
import com.example.shop.product.Product;
import jakarta.persistence.*;
@Entity @Table(name="cart_items",uniqueConstraints=@UniqueConstraint(columnNames={"cart_id","product_id"})) public class CartItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="cart_id",nullable=false) public Cart cart;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="product_id",nullable=false) public Product product;
 @Column(nullable=false) public int quantity;
 protected CartItem(){} public CartItem(Cart cart,Product product,int quantity){this.cart=cart;this.product=product;this.quantity=quantity;}
}
