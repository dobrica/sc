import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { filter } from 'rxjs/operators';
import { disableDebugTools } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Naučni centar';
  tasks: any;

  constructor(private httpClient: HttpClient, private router: Router){}

  redirect(state: string) {
    this.router.navigate(['./' + state]);
  }

  ngOnInit(){
    this.tasks = []; 
    this.httpClient.get('http://localhost:8080/tasks').subscribe(
      (response: any) => { 
        this.tasks = response;
        this.tasks.forEach((field) => { 
          console.log(field.name); 
        });
      },
      (error) => { alert(error.message); }
    );
  }

  getTask(event){
    var target = event.target || event.srcElement || event.currentTarget;
    var idAttr = target.attributes.id;
    console.log("TASK: "+target.attributes.id.value);
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router.navigate(['form/v2/'.concat(target.attributes.id.value)]);

    this.ngOnInit();
  });
  }

}
