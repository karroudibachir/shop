package com.example.shop.product;
import static com.example.shop.product.ProductDtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service @Transactional public class CategoryService {
 private final CategoryRepository categories;private final ProductRepository products;
 public CategoryService(CategoryRepository categories,ProductRepository products){this.categories=categories;this.products=products;}
 @Transactional(readOnly=true) public List<CategoryView> list(){return categories.findAll().stream().map(CategoryView::of).toList();}
 public CategoryView create(CategoryRequest d){return CategoryView.of(categories.save(new Category(d.name().trim(),d.description())));}
 public CategoryView update(long id,CategoryRequest d){Category c=load(id);c.name=d.name().trim();c.description=d.description();return CategoryView.of(c);}
 public void delete(long id){Category c=load(id);if(products.existsByCategoryId(id))throw new ResponseStatusException(HttpStatus.CONFLICT,"Move or remove this category's products first");categories.delete(c);}
 private Category load(long id){return categories.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));}
}
