import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-editor-task',
  templateUrl: './editor-task.component.html',
  styleUrls: ['./editor-task.component.css']
})
export class EditorTaskComponent implements OnInit {

  formFieldsDto: any;
  formFields: any;
  processId: any;
  timeOptions: any;
  coauthors: any;

  constructor(private httpClient: HttpClient, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(
      (params: Params) => {
        this.processId = params['processId'];
      }
    );
    this.httpClient.get('http://localhost:8080/editor/review/'.concat(this.processId)).subscribe(
      (response: any) => {
        this.formFieldsDto = response;
        this.formFields = response.formFields;
        this.formFields.forEach((field) => { 
          if (field.type.name == 'enum' && field.id == 'correctionDuration') {
            this.timeOptions = Object.keys(field.type.values); 
          }
          if (field.type.name == 'enum' && field.id == 'checkCoauthors') {
            this.coauthors = Object.keys(field.type.values); 
          }
        });
      },
      (error) => { alert(error.message); }
    );
  }

  onSubmit(value) {
    var dto = new Array();
    for (var property in value) {
      dto.push({fieldId: property, fieldValue: value[property]});
    }
    let url = "http://localhost:8080/editor/review/scientificPaper/"; debugger
    this.httpClient.post(url.concat(this.formFieldsDto.taskId), dto).subscribe(
      (response) => {
        alert(response)
      }
    );
  }

}
