import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AuthStore } from './auth.store';
export const authInterceptor:HttpInterceptorFn=(request,next)=>{const auth=inject(AuthStore);const token=auth.token();const outgoing=token?request.clone({setHeaders:{Authorization:`Bearer ${token}`}}):request;return next(outgoing).pipe(catchError(error=>{if(error.status===401&&!request.url.includes('/api/auth/'))auth.logoutIfTokenMatches(token);return throwError(()=>error)}))};
