package com.example.shop.cart;
import com.example.shop.account.UserAccount;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="carts") public class Cart {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @OneToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="user_id",nullable=false,unique=true) public UserAccount user;
 @OneToMany(mappedBy="cart",cascade=CascadeType.ALL,orphanRemoval=true) public List<CartItem> items=new ArrayList<>();
 protected Cart(){} public Cart(UserAccount user){this.user=user;}
}
