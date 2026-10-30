package com.example.shop.order;
import com.example.shop.account.UserAccount;
import com.example.shop.cart.*;
import com.example.shop.product.*;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.*;
@Service @Transactional public class OrderService {
 private final OrderRepository orders;private final CartRepository carts;private final ProductRepository products;
 public OrderService(OrderRepository orders,CartRepository carts,ProductRepository products){this.orders=orders;this.carts=carts;this.products=products;}
 public OrderDtos.View checkout(UserAccount user,OrderDtos.Checkout d){Cart c=carts.findByUserId(user.id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart is empty"));if(c.items.isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart is empty");ShopOrder o=new ShopOrder();o.user=user;o.shippingName=d.shippingName().trim();o.shippingAddress=d.shippingAddress().trim();o.shippingCity=d.shippingCity().trim();o.shippingPostalCode=d.shippingPostalCode().trim();o.shippingCountry=d.shippingCountry().trim();o.total=BigDecimal.ZERO;
  for(CartItem ci:c.items){Long productId=ci.product.getId();Product p=products.findLockedById(productId).orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"Product "+productId+" is unavailable"));if(!p.isActive()||p.getStockQuantity()<ci.quantity)throw new ResponseStatusException(HttpStatus.CONFLICT,"Insufficient stock for "+p.getName());p.setStockQuantity(p.getStockQuantity()-ci.quantity);ShopOrderItem oi=new ShopOrderItem(o,p,ci.quantity);o.items.add(oi);o.total=o.total.add(p.getPrice().multiply(BigDecimal.valueOf(ci.quantity)));}ShopOrder saved=orders.save(o);c.items.clear();return view(saved);
 }
 @Transactional(readOnly=true) public Page<OrderDtos.View> list(UserAccount user,int page,int size){return orders.findByUserOrderByCreatedAtDesc(user,PageRequest.of(Math.max(0,page),Math.min(100,Math.max(size,1)))).map(this::view);}
 @Transactional(readOnly=true) public OrderDtos.View get(UserAccount user,long id,boolean admin){ShopOrder o=load(id);if(!admin&&!o.user.getId().equals(user.id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found");return view(o);}
 public OrderDtos.View pay(UserAccount user,long id){ShopOrder o=owned(user,id);if(o.status!=ShopOrder.Status.PENDING)throw new ResponseStatusException(HttpStatus.CONFLICT,"Only pending orders can be paid");o.status=ShopOrder.Status.PAID;return view(o);}
 public OrderDtos.View cancel(UserAccount user,long id){ShopOrder o=owned(user,id);if(o.status!=ShopOrder.Status.PENDING)throw new ResponseStatusException(HttpStatus.CONFLICT,"Only pending orders can be cancelled");o.status=ShopOrder.Status.CANCELLED;for(ShopOrderItem i:o.items)products.findLockedById(i.product.getId()).ifPresent(p->p.setStockQuantity(p.getStockQuantity()+i.quantity));return view(o);}
 @Transactional(readOnly=true) public Page<OrderDtos.View> all(int page,int size){return orders.findAll(PageRequest.of(Math.max(0,page),Math.min(100,Math.max(size,1)),Sort.by(Sort.Direction.DESC,"createdAt"))).map(this::view);}
 public OrderDtos.View status(long id,ShopOrder.Status next){ShopOrder o=load(id);boolean valid=switch(o.status){case PENDING->next==ShopOrder.Status.PAID||next==ShopOrder.Status.CANCELLED;case PAID->next==ShopOrder.Status.SHIPPED||next==ShopOrder.Status.CANCELLED;case SHIPPED->next==ShopOrder.Status.DELIVERED;default->false;};if(!valid)throw new ResponseStatusException(HttpStatus.CONFLICT,"Invalid order status transition");if(next==ShopOrder.Status.CANCELLED)for(ShopOrderItem i:o.items)products.findLockedById(i.product.getId()).ifPresent(p->p.setStockQuantity(p.getStockQuantity()+i.quantity));o.status=next;return view(o);}
 private ShopOrder load(long id){return orders.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found"));}
 private ShopOrder owned(UserAccount user,long id){ShopOrder o=load(id);if(!o.user.getId().equals(user.id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found");return o;}
 private OrderDtos.View view(ShopOrder o){List<OrderDtos.Item> items=o.items.stream().map(i->new OrderDtos.Item(i.product.getId(),i.productName,i.product.getCategoryName(),i.unitPrice,i.quantity,i.unitPrice.multiply(BigDecimal.valueOf(i.quantity)))).toList();return new OrderDtos.View(o.id,o.status,o.shippingName,o.shippingAddress,o.shippingCity,o.shippingPostalCode,o.shippingCountry,o.total,o.createdAt,items);}
}
