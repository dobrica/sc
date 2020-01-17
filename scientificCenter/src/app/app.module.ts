import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { AppComponent } from './app.component';

import { RegisterComponent } from './register/register.component';
import { NewMagazineComponent } from './new-magazine/new-magazine.component';
import { AddMagazineStaffComponent } from './add-magazine-staff/add-magazine-staff.component';
import { GenerateFormComponent } from './generate-form/generate-form.component';
import { RegistrationSuccessComponent } from './registration-success/registration-success.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    NewMagazineComponent,
    AddMagazineStaffComponent,
    GenerateFormComponent,
    RegistrationSuccessComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, 
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
