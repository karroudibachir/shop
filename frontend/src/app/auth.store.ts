import { Injectable, computed, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
export interface UserProfile {id:number;email:string;fullName:string;roles:string[]}
interface AuthResponse {token:string;user:UserProfile}
@Injectable({providedIn:'root'}) export class AuthStore {
 private http=inject(HttpClient);
 readonly token=signal<string|null>(this.readValidToken());
 readonly user=signal<UserProfile|null>(this.readUser());
 readonly isLoggedIn=computed(()=>!!this.token());
 readonly isAdmin=computed(()=>this.user()?.roles.includes('ROLE_ADMIN')??false);
 login(email:string,password:string):Observable<AuthResponse>{return this.http.post<AuthResponse>('/api/auth/login',{email,password}).pipe(tap(r=>this.save(r)))}
 register(email:string,password:string,fullName:string):Observable<AuthResponse>{return this.http.post<AuthResponse>('/api/auth/register',{email,password,fullName}).pipe(tap(r=>this.save(r)))}
 updateProfile(fullName:string):Observable<UserProfile>{return this.http.put<UserProfile>('/api/users/me',{fullName}).pipe(tap(user=>{localStorage.setItem('shop-user',JSON.stringify(user));this.user.set(user)}))}
 logout():void{localStorage.removeItem('shop-token');localStorage.removeItem('shop-user');this.token.set(null);this.user.set(null)}
 logoutIfTokenMatches(token:string|null):void{if(token&&this.token()===token)this.logout()}
 private readValidToken():string|null{const token=localStorage.getItem('shop-token');if(!token)return null;try{const segment=token.split('.')[1];if(!segment)throw new Error('Invalid token');const payload=JSON.parse(atob(segment.replace(/-/g,'+').replace(/_/g,'/'))) as {exp?:number};if(typeof payload.exp!=='number'||payload.exp*1000<=Date.now())throw new Error('Expired token');return token}catch{localStorage.removeItem('shop-token');localStorage.removeItem('shop-user');return null}}
 private save(r:AuthResponse):void{localStorage.setItem('shop-token',r.token);localStorage.setItem('shop-user',JSON.stringify(r.user));this.token.set(r.token);this.user.set(r.user)}
 private readUser():UserProfile|null{try{return JSON.parse(localStorage.getItem('shop-user')||'null') as UserProfile|null}catch{return null}}
}
