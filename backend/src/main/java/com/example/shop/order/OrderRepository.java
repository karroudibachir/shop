package com.example.shop.order;
import com.example.shop.account.UserAccount;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface OrderRepository extends JpaRepository<ShopOrder,Long>{Page<ShopOrder> findByUserOrderByCreatedAtDesc(UserAccount user,Pageable page);long countByStatus(ShopOrder.Status status);@Query("select coalesce(sum(o.total),0) from ShopOrder o where o.status in :statuses") java.math.BigDecimal revenueForStatuses(java.util.Collection<ShopOrder.Status> statuses);}
