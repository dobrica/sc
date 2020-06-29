import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, map } from 'rxjs/operators';
import { User } from 'src/app/types/user';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private router: Router) { }

  getCurrentUser(): String {
    return localStorage.getItem('username');
  }

  login(user: User) {
    return this.http.post('http://localhost:8080/login', user)
    .pipe(
      map(u => {
        if (user) {
          localStorage.setItem('username', user.username);
          localStorage.setItem('currentUser', JSON.stringify(u));
          localStorage.setItem('authToken', JSON.stringify(u));
        }
      })
    );
  }

  logout() {
    return this.http.get('http://localhost:8080/logout').subscribe(
      success => {
        localStorage.removeItem('currentUser');
        localStorage.removeItem('username');
        localStorage.removeItem('authToken');
        this.router.navigate(['/']);
      },
      error => {
        alert('Logout error!');
      });
  }
}
