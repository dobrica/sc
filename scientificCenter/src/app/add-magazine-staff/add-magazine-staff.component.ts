import { Component, OnInit } from '@angular/core';
import { HttpWrapperService } from '../services/http-wrapper/http-wrapper.service';
import { AppSettings } from 'src/app/app-settings/app-settings';

@Component({
  selector: 'app-add-magazine-staff',
  templateUrl: './add-magazine-staff.component.html',
  styleUrls: ['./add-magazine-staff.component.css']
})
export class AddMagazineStaffComponent implements OnInit {

  constructor(service: HttpWrapperService) {
    service.title = "magazine add staff";
    service.endpoint = AppSettings.MAGAZINE_ADD_STAFF_ENDPOINT;
  }

  ngOnInit() {
  }

}
