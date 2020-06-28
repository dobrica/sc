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
          localStorage.setItem('currentUser', JSON.stringify(user));
        }
      })
    );
  }

  logout() {
    return this.http.get('http://localhost:8080/logout').subscribe(
      success => {
        localStorage.removeItem('currentUser');
        localStorage.removeItem('username');
        this.router.navigate(['/']); debugger
      },
      error => {
        alert('Logout error!');
      });
  }
}
