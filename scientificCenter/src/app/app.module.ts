import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { AppComponent } from './app.component';

import { RegisterComponent } from './register/register.component';
import { NewMagazineComponent } from './new-magazine/new-magazine.component';
import { AddMagazineStaffComponent } from './add-magazine-staff/add-magazine-staff.component';
import { GenerateFormComponent } from './generate-form/generate-form.component';
import { RegistrationSuccessComponent } from './registration-success/registration-success.component';
import { LoginComponent } from './login/login.component';

import { AuthService } from './services/auth/auth.service';
import { JwtUtilsService } from './services/jwt-utils/jwt-utils.service';
import { JwtInterceptor } from './_helper/jwt.interceptor';
import { GenerateFormV2Component } from './generate-form-v2/generate-form-v2.component';


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    NewMagazineComponent,
    AddMagazineStaffComponent,
    GenerateFormComponent,
    RegistrationSuccessComponent,
    LoginComponent,
    GenerateFormV2Component
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, 
    HttpClientModule
  ],
  providers: [
    AuthService,
    JwtUtilsService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
