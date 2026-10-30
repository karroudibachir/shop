package com.example.shop.account;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;
@Entity @Table(name="users") public class UserAccount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false,unique=true,length=190) public String email;
 @Column(name="password_hash",nullable=false,length=100) public String passwordHash;
 @Column(name="full_name",nullable=false,length=140) public String fullName;
 @Column(name="created_at",nullable=false) public Instant createdAt=Instant.now();
 @ManyToMany(fetch=FetchType.EAGER) @JoinTable(name="user_roles",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="role_id")) public Set<Role> roles=new HashSet<>();
 public UserAccount(){}
 public Long getId(){return id;}
}
