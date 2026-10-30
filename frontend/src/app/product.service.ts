import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
export interface Product {id:number;name:string;description:string;price:number;stockQuantity:number;imageUrl:string|null;categoryId:number;categoryName:string;active:boolean;createdAt:string}
export interface Category {id:number;name:string;description:string|null}
export interface ProductPage {content:Product[];totalElements:number;totalPages:number;number:number;size:number}
export interface ProductQuery {page:number;size:number;sort:string;q:string;categoryId:string;minPrice:string;maxPrice:string}
export interface ProductInput {name:string;description:string;price:number;stockQuantity:number;imageUrl:string;categoryId:number}
@Injectable({providedIn:'root'}) export class ProductService {
 private http=inject(HttpClient);
 list(query:Partial<ProductQuery>):Observable<ProductPage>{let params=new HttpParams();for(const [key,value] of Object.entries(query)){if(value!==''&&value!==undefined&&value!==null)params=params.set(key,value)}return this.http.get<ProductPage>('/api/products',{params})}
 get(id:number):Observable<Product>{return this.http.get<Product>(`/api/products/${id}`)}
 categories():Observable<Category[]>{return this.http.get<Category[]>('/api/categories')}
 create(input:ProductInput):Observable<Product>{return this.http.post<Product>('/api/products',input)}
 update(id:number,input:ProductInput):Observable<Product>{return this.http.put<Product>(`/api/products/${id}`,input)}
 delete(id:number):Observable<void>{return this.http.delete<void>(`/api/products/${id}`)}
 createCategory(input:{name:string;description:string}):Observable<Category>{return this.http.post<Category>('/api/categories',input)}
 updateCategory(id:number,input:{name:string;description:string}):Observable<Category>{return this.http.put<Category>(`/api/categories/${id}`,input)}
 deleteCategory(id:number):Observable<void>{return this.http.delete<void>(`/api/categories/${id}`)}
}
