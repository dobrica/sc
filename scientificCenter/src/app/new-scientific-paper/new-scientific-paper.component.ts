import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-scientific-paper',
  templateUrl: './new-scientific-paper.component.html',
  styleUrls: ['./new-scientific-paper.component.css']
})
export class NewScientificPaperComponent implements OnInit {

  magazines: any = [];
  formFieldsDto: any;
  formFields: any;

  constructor(private httpClient: HttpClient, private router: Router) { }

  ngOnInit() {
    this.httpClient.get('http://localhost:8080/scientificPaper').subscribe(
      (response: any) => {
        this.formFieldsDto = response;
        this.formFields = response.formFields;
        this.formFields.forEach((field) => {
          if (field.type.name == 'enum') {
            this.magazines = Object.keys(field.type.values);
          }
        });
      },
      (error) => { alert(error.message); }
    );
  }

  onSubmit(value, form) {

    // if (!this.validationService.validate(this.formFieldsDto.formFields, form)) {
    //   return;
    // }

    let dto = new Array();

    for (var property in value) {
      dto.push({ fieldId: property, fieldValue: value[property] });
    }
    this.httpClient.post('http://localhost:8080/chooseMagazine/'.concat(this.formFieldsDto.taskId), dto).subscribe(
      (response: any) => {
        if (response != null && response.openAccess != undefined && response.membershipStatus != undefined) {
          if (!response.openAccess || (response.openAccess && response.membershipStatus)) { // authors dont pay for subscription, or if they do subscription is paid already
            this.router.navigate(['/paperDetailsPage/'.concat(this.formFieldsDto.processInstanceId)]);
          } else if (response.openAccess && !response.membershipStatus) { // payment is necessary, authors pay for subscription
            this.router.navigate(['/subscriptionPayment/'.concat(this.formFieldsDto.processInstanceId)]);
          }
        }else{
          alert("Error: Response is not correct!")
        }
      },
      (error) => {
        alert(error.message);
      }
    );
  }

}
