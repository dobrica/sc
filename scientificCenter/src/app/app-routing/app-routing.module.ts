import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from '../register/register.component'
import { NewMagazineComponent } from '../new-magazine/new-magazine.component';
import { AddMagazineStaffComponent } from '../add-magazine-staff/add-magazine-staff.component';
import { RegistrationSuccessComponent } from '../registration-success/registration-success.component';
import { LoginComponent } from '../login/login.component';

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
  // { path: '', component: HomeComponent },
  { 
    path: 'login',
    component: LoginComponent 
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
