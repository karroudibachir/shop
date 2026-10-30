import { Routes } from '@angular/router';
import { authGuard, roleGuard } from './auth.guard';
export const routes: Routes = [
 {path:'products',loadComponent:()=>import('./pages/products.component').then(m=>m.ProductsComponent)},
 {path:'products/:id',loadComponent:()=>import('./pages/product-detail.component').then(m=>m.ProductDetailComponent)},
 {path:'login',loadComponent:()=>import('./pages/login.component').then(m=>m.LoginComponent)},
 {path:'register',loadComponent:()=>import('./pages/register.component').then(m=>m.RegisterComponent)},
 {path:'cart',loadComponent:()=>import('./pages/cart.component').then(m=>m.CartComponent)},
 {path:'checkout',canActivate:[authGuard],loadComponent:()=>import('./pages/checkout.component').then(m=>m.CheckoutComponent)},
 {path:'orders',canActivate:[authGuard],loadComponent:()=>import('./pages/orders.component').then(m=>m.OrdersComponent)},
 {path:'orders/:id',canActivate:[authGuard],loadComponent:()=>import('./pages/order-detail.component').then(m=>m.OrderDetailComponent)},
 {path:'account',canActivate:[authGuard],loadComponent:()=>import('./pages/account.component').then(m=>m.AccountComponent)},
 {path:'admin',canActivate:[roleGuard],data:{roles:['ROLE_ADMIN']},loadComponent:()=>import('./pages/admin-layout.component').then(m=>m.AdminLayoutComponent),children:[
  {path:'',pathMatch:'full',redirectTo:'dashboard'},
  {path:'dashboard',loadComponent:()=>import('./pages/admin-dashboard.component').then(m=>m.AdminDashboardComponent)},
  {path:'products',loadComponent:()=>import('./pages/admin-products.component').then(m=>m.AdminProductsComponent)},
  {path:'categories',loadComponent:()=>import('./pages/admin-categories.component').then(m=>m.AdminCategoriesComponent)},
  {path:'orders',loadComponent:()=>import('./pages/admin-orders.component').then(m=>m.AdminOrdersComponent)}
 ]},
 {path:'',pathMatch:'full',redirectTo:'products'},
 {path:'**',redirectTo:'products'}
];
