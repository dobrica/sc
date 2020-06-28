import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, Params, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-coauthor',
  templateUrl: './add-coauthor.component.html',
  styleUrls: ['./add-coauthor.component.css']
})
export class AddCoauthorComponent implements OnInit {

  formFieldsDto: any;
  formFields: any;
  processId: any;
  coauthorsNumber: any;

  constructor(private httpClient: HttpClient, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe( 
      (params: Params) => { 
        this.processId = params['processId'];
        this.coauthorsNumber = params['num'];
      }
    );
    this.httpClient.get('http://localhost:8080/coauthor/add/'.concat(this.processId)).subscribe(
      (response: any) => {
        this.formFieldsDto = response;
        this.formFields = response.formFields;
      },
      (error) => { alert(error.message); }
    );
  }

  onSubmit(value) {
    let dto = new Array();

    for (var property in value) {
      dto.push({ fieldId: property, fieldValue: value[property] });
    }
    let url = 'http://localhost:8080/coauthor/add/'.concat(this.formFieldsDto.taskId);
    this.httpClient.post(url, dto).subscribe(
      (response: any) => { 
        this.coauthorsNumber = this.coauthorsNumber - 1;
        if (this.coauthorsNumber >= 1) {
          this.router.navigate(['/coauthor/'.concat(this.coauthorsNumber, "/", this.formFieldsDto.processInstanceId)]);
          this.ngOnInit();
        }
        else {
          this.router.navigate(['/editor/review/'.concat(this.formFieldsDto.processInstanceId)]);
        }
      },
      (error) => {
        alert(error.message);
      }
    );
  }

}
