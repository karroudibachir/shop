import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
interface OrderRow{id:number;status:string;total:number;createdAt:string}
@Component({standalone:true,imports:[CurrencyPipe,DatePipe,RouterLink],changeDetection:ChangeDetectionStrategy.OnPush,template:`<section class="shop-page"><div class="eyebrow">A LITTLE HISTORY</div><h1>Your <em>orders.</em></h1>@if(loading()){<div class="state">Finding your orders…</div>}@else if(rows().length===0){<div class="state">No orders yet. <a routerLink="/products">Find something for your home</a></div>}@else{<div class="order-list">@for(order of rows();track order.id){<a [routerLink]="['/orders',order.id]" class="order-row"><div><strong>Order #{{order.id}}</strong><p>{{order.createdAt|date:'mediumDate'}}</p></div><span class="status">{{order.status}}</span><strong>{{order.total|currency:'USD'}}</strong><span>↗</span></a>}</div>}</section>`})
export class OrdersComponent {private http=inject(HttpClient);rows=signal<OrderRow[]>([]);loading=signal(true);constructor(){this.http.get<{content:OrderRow[]}>('/api/orders').subscribe({next:r=>{this.rows.set(r.content);this.loading.set(false)},error:()=>this.loading.set(false)})}}
