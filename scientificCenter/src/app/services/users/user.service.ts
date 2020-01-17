import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  fetchUsers() {
    return this.httpClient.get("http://localhost:8080/user/fetch") as Observable<any>;
  }

  registerUser(user, taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/post/".concat(taskId), user) as Observable<any>;
  }
}
