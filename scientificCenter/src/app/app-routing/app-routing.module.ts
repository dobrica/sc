import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from '../register/register.component'
import { NewMagazineComponent } from '../new-magazine/new-magazine.component';
import { AddMagazineStaffComponent } from '../add-magazine-staff/add-magazine-staff.component';
import { RegistrationSuccessComponent } from '../registration-success/registration-success.component';
import { LoginComponent } from '../login/login.component';
import { GenerateFormV2Component } from '../generate-form-v2/generate-form-v2.component';

const routes: Routes = [
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'registration/success',
    component: RegistrationSuccessComponent
  },
  {
    path: 'magazine/create',
    component: NewMagazineComponent
  },
  {
    path: 'magazine/staff/add',
    component: AddMagazineStaffComponent
  },
  { 
    path: 'login',
    component: LoginComponent 
  },
  // { 
  //   path: 'subscriptionPayment/:processId',
  //   component: SubscriptionPaymentComponent 
  // },
  {
    path: 'form/v2/:taskId',
    component: GenerateFormV2Component
  }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
