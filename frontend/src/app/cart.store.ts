import { Injectable, computed, inject, signal } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, firstValueFrom, tap } from 'rxjs';
import { AuthStore } from './auth.store';
import { Product } from './product.service';
export interface CartLine {productId:number;name:string;categoryName:string;imageUrl:string|null;unitPrice:number;quantity:number;lineTotal:number;stockQuantity:number}
export interface CartView {items:CartLine[];itemCount:number;subtotal:number}
const empty:CartView={items:[],itemCount:0,subtotal:0};
@Injectable({providedIn:'root'}) export class CartStore {
 private http=inject(HttpClient);private auth=inject(AuthStore);
 readonly cart=signal<CartView>(this.readGuest());readonly items=computed(()=>this.cart().items);readonly itemCount=computed(()=>this.cart().itemCount);readonly subtotal=computed(()=>this.cart().subtotal);
 load():void{if(this.auth.isLoggedIn())this.http.get<CartView>('/api/cart').subscribe({next:c=>this.cart.set(c),error:()=>{if(!this.auth.isLoggedIn())this.cart.set(this.readGuest())}});else this.cart.set(this.readGuest())}
 add(product:Product,quantity:number):void{if(this.auth.isLoggedIn()){this.http.post<CartView>('/api/cart/items',{productId:product.id,quantity}).subscribe({next:c=>this.cart.set(c),error:(error:HttpErrorResponse)=>{if(error.status===401)this.addGuest(product,quantity)}});return}this.addGuest(product,quantity)}
 addServer(productId:number,quantity:number):Observable<CartView>{return this.http.post<CartView>('/api/cart/items',{productId,quantity}).pipe(tap(c=>this.cart.set(c)))}
 update(productId:number,quantity:number):void{if(this.auth.isLoggedIn()){this.http.patch<CartView>(`/api/cart/items/${productId}`,{quantity}).subscribe({next:c=>this.cart.set(c),error:(error:HttpErrorResponse)=>{if(error.status===401)this.set(this.items().map(i=>i.productId===productId?{...i,quantity}:i))}});return}this.set(this.items().map(i=>i.productId===productId?{...i,quantity}:i))}
 remove(productId:number):void{if(this.auth.isLoggedIn()){this.http.delete<CartView>(`/api/cart/items/${productId}`).subscribe({next:c=>this.cart.set(c),error:(error:HttpErrorResponse)=>{if(error.status===401)this.set(this.items().filter(i=>i.productId!==productId))}});return}this.set(this.items().filter(i=>i.productId!==productId))}
 clear():void{if(this.auth.isLoggedIn()){this.http.delete<CartView>('/api/cart').subscribe({next:c=>this.cart.set(c),error:(error:HttpErrorResponse)=>{if(error.status===401)this.set([])}});return}this.set([])}
 async mergeGuest():Promise<boolean>{if(!this.auth.isLoggedIn())return false;const guest=this.readGuest();try{for(const line of guest.items)await firstValueFrom(this.addServer(line.productId,line.quantity));localStorage.removeItem('guest-cart');localStorage.removeItem('shop-guest-cart');this.load();return true}catch{this.load();return false}}
 private addGuest(product:Product,quantity:number):void{const items=[...this.items()];const current=items.find(i=>i.productId===product.id);if(current)current.quantity+=quantity;else items.push({productId:product.id,name:product.name,categoryName:product.categoryName,imageUrl:product.imageUrl,unitPrice:product.price,quantity,lineTotal:product.price*quantity,stockQuantity:product.stockQuantity});this.set(items)}
 private set(items:CartLine[]):void{const normalized=items.map(i=>({...i,lineTotal:i.unitPrice*i.quantity}));this.cart.set({items:normalized,itemCount:normalized.reduce((n,i)=>n+i.quantity,0),subtotal:normalized.reduce((n,i)=>n+i.lineTotal,0)});localStorage.setItem('shop-guest-cart',JSON.stringify(this.cart()))}
 private readGuest():CartView{try{const parsed=JSON.parse(localStorage.getItem('shop-guest-cart')||'null') as CartView|null;if(parsed){parsed.items=parsed.items.map(item=>({...item,categoryName:item.categoryName||''}));return parsed}const old=JSON.parse(localStorage.getItem('guest-cart')||'{}') as Record<string,number>;return{...empty,items:Object.entries(old).map(([id,quantity])=>({productId:Number(id),name:'Product',categoryName:'',imageUrl:null,unitPrice:0,quantity,lineTotal:0,stockQuantity:99})),itemCount:Object.values(old).reduce((a,b)=>a+b,0)}}catch{return empty}}
}
