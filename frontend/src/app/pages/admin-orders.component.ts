import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

interface AdminOrder {
  id: number;
  status: string;
  total: number;
  createdAt: string;
  shippingName: string;
  shippingCity: string;
}

@Component({
  standalone: true,
  imports: [CurrencyPipe, DatePipe, FormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `<section class="shop-page">
    <div class="eyebrow">STORE ADMINISTRATION</div>
    <h1>Order <em>management.</em></h1>
    <div class="admin-table admin-orders">
      @for (order of orders(); track order.id) {
        <article class="admin-order">
          <div><strong>Order #{{order.id}}</strong><p>{{order.shippingName}} · {{order.shippingCity}}</p><small>{{order.createdAt|date:'medium'}}</small></div>
          <strong>{{order.total|currency:'USD'}}</strong>
          <select [ngModel]="order.status" (ngModelChange)="setStatus(order,$event)">
            @for (state of choices(order.status); track state) { <option [value]="state">{{state}}</option> }
          </select>
        </article>
      }
    </div>
    @if (error()) { <p class="form-error">{{error()}}</p> }
  </section>`
})
export class AdminOrdersComponent {
  private http = inject(HttpClient);
  orders = signal<AdminOrder[]>([]);
  error = signal('');

  constructor() { this.load(); }

  load(): void {
    this.http.get<{content: AdminOrder[]}>('/api/admin/orders?page=0&size=100').subscribe({
      next: page => this.orders.set(page.content),
      error: () => this.error.set('Could not load orders. Check admin access.')
    });
  }

  choices(status: string): string[] {
    switch (status) {
      case 'PENDING': return ['PENDING', 'PAID', 'CANCELLED'];
      case 'PAID': return ['PAID', 'SHIPPED', 'CANCELLED'];
      case 'SHIPPED': return ['SHIPPED', 'DELIVERED'];
      default: return [status];
    }
  }

  setStatus(order: AdminOrder, next: string): void {
    if (next === order.status) return;
    this.http.patch(`/api/admin/orders/${order.id}/status`, {status: next}).subscribe({
      next: () => this.load(),
      error: () => this.error.set('That status transition is not allowed.')
    });
  }
}
