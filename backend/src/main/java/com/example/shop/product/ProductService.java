package com.example.shop.product;

import static com.example.shop.product.ProductDtos.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service @Transactional(readOnly=true)
public class ProductService {
 private final ProductRepository products; private final CategoryRepository categories;
 public ProductService(ProductRepository products,CategoryRepository categories){this.products=products;this.categories=categories;}
 public Page<ProductView> list(int page,int size,String sort,String q,Long categoryId,java.math.BigDecimal minPrice,java.math.BigDecimal maxPrice){
  String field=switch(sort==null?"newest":sort){case "price"->"price";case "name"->"name";default->"createdAt";}; Sort order=Sort.by(field.equals("createdAt")?Sort.Direction.DESC:Sort.Direction.ASC,field);
  Specification<Product> spec=(root,query,cb)->cb.isTrue(root.get("active"));
  if(q!=null&&!q.isBlank()) spec=spec.and((r,x,c)->c.like(c.lower(r.get("name")),"%"+q.toLowerCase()+"%"));
  if(categoryId!=null) spec=spec.and((r,x,c)->c.equal(r.get("category").get("id"),categoryId));
  if(minPrice!=null) spec=spec.and((r,x,c)->c.greaterThanOrEqualTo(r.get("price"),minPrice));
  if(maxPrice!=null) spec=spec.and((r,x,c)->c.lessThanOrEqualTo(r.get("price"),maxPrice));
  return products.findAll(spec,PageRequest.of(Math.max(0,page),Math.min(100,Math.max(1,size)),order)).map(ProductView::of);
 }
 public ProductView get(long id){return products.findById(id).filter(p->p.active).map(ProductView::of).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));}
 @Transactional public ProductView create(ProductRequest d){Product p=new Product(d.name(),d.description(),d.price(),d.stockQuantity(),d.imageUrl(),category(d.categoryId()));return ProductView.of(products.save(p));}
 @Transactional public ProductView update(long id,ProductRequest d){Product p=products.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));p.name=d.name();p.description=d.description();p.price=d.price();p.stockQuantity=d.stockQuantity();p.imageUrl=d.imageUrl();p.category=category(d.categoryId());return ProductView.of(p);}
 @Transactional public void delete(long id){Product p=products.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));p.active=false;}
 private Category category(Long id){return categories.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unknown category"));}
}
