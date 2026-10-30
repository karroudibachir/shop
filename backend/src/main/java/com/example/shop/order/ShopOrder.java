package com.example.shop.order;
import com.example.shop.account.UserAccount;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@Entity @Table(name="orders",indexes=@Index(name="idx_order_user",columnList="user_id,created_at")) public class ShopOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="user_id",nullable=false) public UserAccount user;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) public Status status=Status.PENDING;
 @Column(name="shipping_name",nullable=false,length=140) public String shippingName;
 @Column(name="shipping_address",nullable=false,length=300) public String shippingAddress;
 @Column(name="shipping_city",nullable=false,length=100) public String shippingCity;
 @Column(name="shipping_postal_code",nullable=false,length=30) public String shippingPostalCode;
 @Column(name="shipping_country",nullable=false,length=100) public String shippingCountry;
 @Column(nullable=false,precision=12,scale=2) public BigDecimal total;
 @Column(name="created_at",nullable=false) public Instant createdAt=Instant.now();
 @OneToMany(mappedBy="order",cascade=CascadeType.ALL,orphanRemoval=true) public List<ShopOrderItem> items=new ArrayList<>();
 protected ShopOrder(){}
 public enum Status {PENDING,PAID,SHIPPED,DELIVERED,CANCELLED}
}
