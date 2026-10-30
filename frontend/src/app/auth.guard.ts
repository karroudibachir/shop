import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthStore } from './auth.store';
export const authGuard:CanActivateFn=(_route,state)=>{const auth=inject(AuthStore);const router=inject(Router);return auth.isLoggedIn()?true:router.createUrlTree(['/login'],{queryParams:{returnUrl:state.url}})};
export const adminGuard:CanActivateFn=()=>{const auth=inject(AuthStore);return auth.isAdmin()?true:(auth.isLoggedIn()?inject(Router).createUrlTree(['/products']):inject(Router).createUrlTree(['/login']))};
export const roleGuard:CanActivateFn=(route)=>{const auth=inject(AuthStore);const router=inject(Router);if(!auth.isLoggedIn())return router.createUrlTree(['/login']);const required=route.data['roles'] as string[]|undefined;if(!required?.some(role=>auth.user()?.roles.includes(role)))return router.createUrlTree(['/products']);return true};
