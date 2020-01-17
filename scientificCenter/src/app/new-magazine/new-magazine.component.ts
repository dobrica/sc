import { Component, OnInit } from '@angular/core';
import { HttpWrapperService } from '../services/http-wrapper/http-wrapper.service';
import { AppSettings } from 'src/app/app-settings/app-settings';
import { GenerateFormComponent } from '../generate-form/generate-form.component';

@Component({
  selector: 'app-new-magazine',
  templateUrl: './new-magazine.component.html',
  styleUrls: ['./new-magazine.component.css']
})
export class NewMagazineComponent {

  constructor(service: HttpWrapperService) {
    service.title = "new magazine";
    service.endpoint = AppSettings.NEW_MAGAZINE_ENDPOINT;
  }

}
