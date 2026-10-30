package com.example.shop.admin;
import com.example.shop.account.UserRepository;
import com.example.shop.order.*;
import com.example.shop.product.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
@Service @Transactional(readOnly=true) public class AdminDashboardService {
 private final ProductRepository products;private final CategoryRepository categories;private final UserRepository users;private final OrderRepository orders;
 public AdminDashboardService(ProductRepository products,CategoryRepository categories,UserRepository users,OrderRepository orders){this.products=products;this.categories=categories;this.users=users;this.orders=orders;}
 public Stats stats(){long pending=orders.countByStatus(ShopOrder.Status.PENDING);long paid=orders.countByStatus(ShopOrder.Status.PAID);long shipped=orders.countByStatus(ShopOrder.Status.SHIPPED);long delivered=orders.countByStatus(ShopOrder.Status.DELIVERED);BigDecimal revenue=orders.revenueForStatuses(List.of(ShopOrder.Status.PAID,ShopOrder.Status.SHIPPED,ShopOrder.Status.DELIVERED));return new Stats(products.countByActiveTrue(),categories.count(),users.count(),orders.count(),pending,paid,shipped,delivered,revenue);}
 public record Stats(long activeProducts,long categories,long customers,long orders,long pendingOrders,long paidOrders,long shippedOrders,long deliveredOrders,BigDecimal revenue){}
}
