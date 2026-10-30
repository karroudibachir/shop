package com.example.shop.product;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name="products", indexes={@Index(name="idx_product_category_active", columnList="category_id,active"),@Index(name="idx_product_name", columnList="name")})
public class Product {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @Column(nullable=false,length=160) public String name;
    @Column(nullable=false,columnDefinition="text") public String description;
    @Column(nullable=false,precision=10,scale=2) public BigDecimal price;
    @Column(name="stock_quantity",nullable=false) public int stockQuantity;
    @Column(name="image_url",length=500) public String imageUrl;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="category_id",nullable=false) public Category category;
    @Column(nullable=false) public boolean active=true;
    @Column(name="created_at",nullable=false) public Instant createdAt=Instant.now();
    @Column(name="updated_at",nullable=false) public Instant updatedAt=Instant.now();
    protected Product() {}
    public Long getId(){return id;} public String getName(){return name;} public String getCategoryName(){return category==null?"Uncategorized":category.getName();} public BigDecimal getPrice(){return price;} public String getImageUrl(){return imageUrl;} public int getStockQuantity(){return stockQuantity;} public boolean isActive(){return active;} public void setStockQuantity(int value){stockQuantity=value;}
    public Product(String name,String description,BigDecimal price,int stockQuantity,String imageUrl,Category category) { this.name=name;this.description=description;this.price=price;this.stockQuantity=stockQuantity;this.imageUrl=imageUrl;this.category=category; }
    @PreUpdate void touch(){updatedAt=Instant.now();}
}
