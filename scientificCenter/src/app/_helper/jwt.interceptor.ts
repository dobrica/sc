import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import {JwtHelperService} from '@auth0/angular-jwt';
import { AuthService } from '../services/auth/auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  public helper = new JwtHelperService();

  constructor(private authService: AuthService, private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser != null) {
      if (currentUser.accessToken) {
        let isExpired = this.helper.isTokenExpired(currentUser.accessToken);

        if (isExpired) {
          alert('Session is expired');
          localStorage.removeItem('currentUser');
          this.router.navigate(['/']);
        } else {
          if (currentUser && currentUser.accessToken) {
            request = request.clone({
              setHeaders: {
                Authorization: `Bearer ${currentUser.accessToken}`
              }
            });
          }
        }
      }
    }
    return next.handle(request);
  }
}
