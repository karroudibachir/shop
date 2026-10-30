package com.example.shop.cart;
import com.example.shop.account.UserAccount;
import com.example.shop.product.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.*;
@Service @Transactional public class CartService {
 private final CartRepository carts;private final ProductRepository products;
 public CartService(CartRepository carts,ProductRepository products){this.carts=carts;this.products=products;}
 public CartDtos.View get(UserAccount user){return view(cart(user));}
 public CartDtos.View add(UserAccount user,CartDtos.AddItem body){Cart c=cart(user);Product p=active(body.productId());CartItem item=c.items.stream().filter(i->i.product.getId().equals(p.id)).findFirst().orElse(null);int next=(item==null?0:item.quantity)+body.quantity();stock(p,next);if(item==null)c.items.add(new CartItem(c,p,body.quantity()));else item.quantity=next;return view(c);}
 public CartDtos.View set(UserAccount user,long productId,CartDtos.Quantity body){Cart c=cart(user);CartItem i=item(c,productId);stock(i.product,body.quantity());i.quantity=body.quantity();return view(c);}
 public CartDtos.View remove(UserAccount user,long productId){Cart c=cart(user);c.items.removeIf(i->i.product.getId()==productId);return view(c);}
 public CartDtos.View clear(UserAccount user){Cart c=cart(user);c.items.clear();return view(c);}
 Cart cart(UserAccount u){return carts.findByUserId(u.id).orElseGet(()->carts.save(new Cart(u)));}
 private CartItem item(Cart c,long id){return c.items.stream().filter(i->i.product.getId()==id).findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Cart item not found"));}
 private Product active(long id){return products.findById(id).filter(p->p.active).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));}
 private void stock(Product p,int quantity){if(quantity<1)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantity must be at least one");if(!p.isActive()||p.getStockQuantity()<quantity)throw new ResponseStatusException(HttpStatus.CONFLICT,"Insufficient stock");}
 CartDtos.View view(Cart c){List<CartDtos.Item> items=c.items.stream().map(i->{Product p=i.product;String categoryName=p.category==null?"Uncategorized":p.category.getName();return new CartDtos.Item(p.getId(),p.getName(),categoryName,p.getImageUrl(),p.getPrice(),i.quantity,p.getPrice().multiply(BigDecimal.valueOf(i.quantity)),p.getStockQuantity());}).toList();return new CartDtos.View(items,items.stream().mapToInt(CartDtos.Item::quantity).sum(),items.stream().map(CartDtos.Item::lineTotal).reduce(BigDecimal.ZERO,BigDecimal::add));}
}
