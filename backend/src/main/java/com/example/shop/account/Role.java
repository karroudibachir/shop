package com.example.shop.account;
import jakarta.persistence.*;
@Entity @Table(name="roles") public class Role { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @Column(nullable=false,unique=true) public String name; protected Role(){} public Role(String name){this.name=name;} }
