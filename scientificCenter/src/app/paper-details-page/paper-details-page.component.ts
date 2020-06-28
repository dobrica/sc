import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-paper-details-page',
  templateUrl: './paper-details-page.component.html',
  styleUrls: ['./paper-details-page.component.css']
})
export class PaperDetailsPageComponent implements OnInit {

  formFieldsDto: any;
  formFields: any;
  processId: any;
  scientificFields: any;
  coauthorsNumber: any;
  selectedFileName = "";
  fileUrl: string;
  fileToUpload: File;

  constructor(private httpClient: HttpClient, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe( 
      (params: Params) => { 
        this.processId = params['processId'];
      }
    );
    this.httpClient.get('http://localhost:8080/paperDetails/'.concat(this.processId)).subscribe(
      (response: any) => {
        this.formFieldsDto = response;
        this.formFields = response.formFields; 
        this.formFields.forEach( (field) => {
          if ( field.type.name == 'enum') {
            this.scientificFields = Object.keys(field.type.values);
          }
        });
      },
      (error) => { alert(error.message); } 
    );
  }

  onSubmit(value) {
    let dto = new Array();

    for (var property in value) {
      if (property == "coauthorsNumber") { 
        this.coauthorsNumber = value[property];
      } 
      dto.push({ fieldId: property, fieldValue: value[property] });
    } 
    let url = 'http://localhost:8080/paperDetails/create/'.concat(this.formFieldsDto.taskId);
    this.httpClient.post(url, dto).subscribe(
      (response: any) => { 
        this.router.navigate(['/']);
      },
      (error) => {
        alert(error.message);
      }
    );
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
