import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';
import { AppSettings } from 'src/app/app-settings/app-settings';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient, private http : HttpClient) { 
    

  }

  startProcess(){
    return this.httpClient.get(AppSettings.REGISTRATION_ENDPOINT) as Observable<any>
  }

  getTasks(processInstance : string){

    return this.httpClient.get('http://localhost:8080/welcome/get/tasks/'.concat(processInstance)) as Observable<any>
  }

  claimTask(taskId){
    return this.httpClient.post('http://localhost:8080/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>
  }

  completeTask(taskId){
    return this.httpClient.post('http://localhost:8080/welcome/tasks/complete/'.concat(taskId), null) as Observable<any>
  }

  showTask(taskId){
    return this.httpClient.get('http://localhost:8080/welcome/get/task/'.concat(taskId)) as Observable<any>
  }

}
