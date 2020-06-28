import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../services/auth/auth.service';
import { User } from '../types/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  model: any = {};
  loggedUser: any;

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private client: HttpClient
  ) { }

  ngOnInit() {
    // sessionStorage.setItem('token', ''); 
  }

  onSubmit() {
    let user = new User();
    user.username = this.model.username;
    user.password = this.model.password;

    this.authService.login(user).subscribe(
      (success) => {
        this.router.navigate(['/']); debugger
        alert("success");
      },
      (error) => {
        alert(error);
      });
  }
}