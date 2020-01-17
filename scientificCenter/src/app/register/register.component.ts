import { Component, OnInit } from '@angular/core';
import { AppSettings } from 'src/app/app-settings/app-settings';
import { HttpWrapperService } from '../services/http-wrapper/http-wrapper.service';
import { GenerateFormComponent } from '../generate-form/generate-form.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  
  constructor(service: HttpWrapperService) {
    service.title = "register";
    service.endpoint = AppSettings.REGISTRATION_ENDPOINT;
  }

}
