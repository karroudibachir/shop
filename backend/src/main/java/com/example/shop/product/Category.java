package com.example.shop.product;

import jakarta.persistence.*;

@Entity @Table(name="categories")
public class Category {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @Column(nullable=false, unique=true, length=80) public String name;
    @Column(length=500) public String description;
    protected Category() {}
    public Long getId(){return id;} public String getName(){return name;}
    public Category(String name, String description) { this.name=name; this.description=description; }
}
