import { Component, OnInit, NgModule } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute, Params, RouterModule } from '@angular/router';
import { ValidationService } from '../services/validation/validation.service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-generate-form-v2',
  templateUrl: './generate-form-v2.component.html',
  styleUrls: ['./generate-form-v2.component.css']
})

export class GenerateFormV2Component implements OnInit {

  url: any
  taskId: any;
  magazines: any;
  scientificFields: any;
  formFieldsDto: any;
  formFields: any;
  processId: any;
  reviewerOptions: any;
  selectedFileName = "";
  fileUrl: string;
  fileToUpload: File;
  timeOptions: any;
  taskName: any;
  reviewOptions: string[];
  magazine: any;

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private validation: ValidationService) { }

  ngOnInit() {

    this.route.params.subscribe(
      (params: Params) => {
        this.taskId = params['taskId'];
      }
    );

    this.httpClient.get('http://localhost:8080/task/'.concat(this.taskId)).subscribe(
      (response: any) => {
        this.taskName = response.taskName;

        let url = ""; 
        switch (this.taskName) {
          case "Unesi podatke o naucnom radu":
            url = 'http://localhost:8080/paperDetails/'.concat(this.taskId);
            break;
          case "Zadatak urednika":
            url = 'http://localhost:8080/editor/review/'.concat(this.taskId);
            break;
          case "Dodaj koautora":
            url = 'http://localhost:8080/coauthor/add/'.concat(this.taskId);
            break;
          case "Izaberi casopis":
            url = 'http://localhost:8080/scientificPaper/'.concat(this.taskId);
            break;
          case "Izbor recenzenata":
            url = 'http://localhost:8080/chooseReviewers/'.concat(this.taskId);
            break;
          case "Autorska dorada rada":
            url = 'http://localhost:8080/corrections/'.concat(this.taskId);
            break;
          case "Kreiranje komisije":
            url = 'http://localhost:8080/committee/'.concat(this.taskId);
            break;
          case "Recenzije":
            url = 'http://localhost:8080/review/'.concat(this.taskId);
            break;
          case "Uplati clanarinu":
            url = 'http://localhost:8080/payment/'.concat(this.taskId); 
            break;
          case "newProcess":
            url = 'http://localhost:8080/scientificPaper';
            break;
        }

        this.httpClient.get(url).subscribe(
          (response: any) => { 
            this.formFieldsDto = response;
            this.formFields = response.formFields;
            this.formFields.forEach((field) => {
              if (field.type.name == 'enum') {
                this.magazines = Object.keys(field.type.values);
              }
              if (field.type.name == 'enum' && field.id == 'correctionDuration') {
                this.timeOptions = Object.keys(field.type.values);
              }
              if (field.type.name == 'enum' && field.id == 'reviewDuration') {
                this.timeOptions = Object.keys(field.type.values);
              }
              if (field.type.name == 'enum' && field.id == 'reviewer') {
                this.reviewerOptions = Object.keys(field.type.values);
              }
              if (field.type.name == 'enum') {
                this.scientificFields = Object.keys(field.type.values);
              }
              if (field.type.name == 'enum' && field.id == 'recommendation') {
                this.reviewOptions = Object.keys(field.type.values);
              }
            });
          },
          (error) => { alert(error.message); }
        );

      },
      (error) => { alert(error.message); }
    );

  }

  cleanUp() {

  }

  onSubmit(value, form) {

    if (!this.validation.validate(this.formFieldsDto.formFields, form)) { 
      return;
    }

    let dto = new Array();
    for (var property in value) {
      dto.push({ fieldId: property, fieldValue: value[property] });
    }
    let url = "http://localhost:8080/task/".concat(this.formFieldsDto.taskId);
    this.httpClient.post(url, dto, {responseType: 'text'}).subscribe(
      (response: any) => {
        let id = response; debugger
        
        if (this.fileToUpload != undefined && this.fileToUpload != null) {
          const formData: FormData = new FormData();
          formData.append('file', this.fileToUpload);
          console.log(formData); 
          this.httpClient.put('http://localhost:8080/scientificPaper/'.concat(id), formData, {responseType: 'text'}).subscribe(); 
        }

        window.location.reload();
      },
      (error) => {
        alert(error.message);
      }
    );
    this.router.navigate(['/']);
  }

  handleFileInput(file: FileList) {
    this.fileToUpload = file.item(0);
    var reader = new FileReader();
    reader.onload = (event: any) => {
      this.fileUrl = event.target.result;
    };
    reader.readAsDataURL(this.fileToUpload);
    this.selectedFileName = this.fileToUpload.name;
    console.log('URL ' + this.fileUrl);
    console.log('file ' + this.fileToUpload);
    console.log('filename ' + this.fileToUpload.name);
  }

}
