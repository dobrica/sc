import { Component, OnInit, Input } from '@angular/core';
import { HttpWrapperService } from '../services/http-wrapper/http-wrapper.service';
import { AppSettings } from 'src/app/app-settings/app-settings';

@Component({
  selector: 'app-generate-form',
  templateUrl: './generate-form.component.html',
  styleUrls: ['./generate-form.component.css']
})
export class GenerateFormComponent {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private success: boolean = false;
  private service: HttpWrapperService;
  private title;

  constructor(service: HttpWrapperService) {
    this.service = service;
    this.title = service.title;
    this.success = false;
    let x = service.get(this.service.endpoint, AppSettings.NO_PARAMS); 

    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach((field) => {

          if (field.type.name == 'enum') {
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    console.log(o);
    let x = this.service.post(this.service.endpoint, this.formFieldsDto.taskId, o);

    x.subscribe(
      res => {
        console.log(res);
        this.success = true && this.title == "register";
        this.getTasks();
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  getTasks() {
    let x = this.service.get(AppSettings.TASKS_ENDPOINT, this.processInstance);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  showTask(taskId) {
    let x = this.service.get(AppSettings.TASK_ENDPOINT, taskId);
    x.subscribe(
      res => {
        console.log(res);
        this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach((field) => {

          if (field.type.name == 'enum') {
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured");
      }
    );
  }

}
