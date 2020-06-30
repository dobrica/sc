import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  constructor() { }

  validate(formFields: any, form: NgForm) { 
    let isValid = true;

    formFields.forEach((field) => {
      // required fields
      if (isValid == false) { 
        return;
      }

      for (var validator of field.validationConstraints) {
        if (validator.name == 'required') { 
          if (form.controls[field.id].value == '' 
          || form.controls[field.id].value == null
          || form.controls[field.id].value == undefined) {
            isValid = false; 
            form.controls[field.id].setErrors({'required': true});
            alert(field.label + ' je obavezno polje!');
          }
        }
      }

    });

    return isValid;
  }
}
