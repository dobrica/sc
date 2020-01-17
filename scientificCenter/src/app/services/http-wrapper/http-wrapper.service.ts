import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppSettings } from 'src/app/app-settings/app-settings';

@Injectable({
  providedIn: 'root'
})
export class HttpWrapperService {

  endpoint: string;
  title: string;

  constructor(private httpClient: HttpClient) { }
  
  get(url: string, arg1) {
    return this.httpClient.get(this.getFullUrl(url, arg1), {withCredentials: true, reportProgress: true, headers: {
        'Content-Type' : 'application/json; charset=utf-8',
        'Accept'       : 'application/json',
        'Authorization': `Basic ${sessionStorage.getItem('token')}`, 
    }}) as Observable<any>
  }

  post(url, arg1, arg2) { 
    return this.httpClient.post(this.getFullUrl(url, arg1), arg2, {withCredentials: true, reportProgress: true, headers: {
        'Content-Type' : 'application/json; charset=utf-8',
        'Accept'       : 'application/json',
        'Authorization': `Basic ${sessionStorage.getItem('token')}`, 
    }}) as Observable<any>;
  }

  getFullUrl(url: string, arg) {
    if(arg == "" || arg == null) {
      return url; 
    }
    else if(arg != "" || arg != null) {
      return url + "/" + arg;
    }
  }

}
