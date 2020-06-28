import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {catchError, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedUser: any;

  constructor(private http: HttpClient, private router: Router) { }

  getLoggedUser() {
    return localStorage.getItem('currentUser');
  }

  login(user) {
    return this.http.post('http://localhost:8080/login', user)
      .pipe(
        map(user => {
          this.loggedUser = user;
          if (user) {
            localStorage.setItem('currentUser', JSON.stringify(user));
          }
          return user;
        })
      );
  }

  logout() {
    return this.http.post('http://localhost:8080/logout', null).subscribe(
      success => {
        localStorage.removeItem('currentUser');
        this.router.navigate(['']);
      }, error => alert('Error while trying to logout.')
    );
  }
}
