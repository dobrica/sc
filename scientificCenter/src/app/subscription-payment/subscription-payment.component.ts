import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-subscription-payment',
  templateUrl: './subscription-payment.component.html',
  styleUrls: ['./subscription-payment.component.css']
})
export class SubscriptionPaymentComponent implements OnInit {

  formFieldsDto: any;
  formFields: any;
  processId: any;

  constructor(private httpClient: HttpClient, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe( 
      (params: Params) => { 
        this.processId = params['processId'];
      }
    );
    this.httpClient.get('http://localhost:8080/payment/'.concat(this.processId)).subscribe(
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
    let url = 'http://localhost:8080/payment/create/'.concat(this.formFieldsDto.taskId, '/', this.processId);
    this.httpClient.post(url, dto).subscribe(
      (response: any) => { 
          alert("Success!") //REDIRECT
      },
      (error) => {
        alert(error.message);
      }
    );
  }

}
