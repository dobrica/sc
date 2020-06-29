import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { AuthService } from '../services/auth/auth.service';
import { User } from '../types/user';
import { HttpClient } from '@angular/common/http';

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
    private client: HttpClient,
    private router: Router,
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
        window.location.reload();
      },
      (error) => {
        alert(error);
      });
    this.router.navigate(['/']);
  }
}