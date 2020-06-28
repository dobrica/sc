import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from '../register/register.component'
import { NewMagazineComponent } from '../new-magazine/new-magazine.component';
import { AddMagazineStaffComponent } from '../add-magazine-staff/add-magazine-staff.component';
import { RegistrationSuccessComponent } from '../registration-success/registration-success.component';
import { LoginComponent } from '../login/login.component';
import { NewScientificPaperComponent } from '../new-scientific-paper/new-scientific-paper.component';
import { PaperDetailsPageComponent } from '../paper-details-page/paper-details-page.component';
import { SubscriptionPaymentComponent } from '../subscription-payment/subscription-payment.component';
import { AddCoauthorComponent } from '../add-coauthor/add-coauthor.component';
import { EditorTaskComponent } from '../editor-task/editor-task.component';
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
  //   path: 'scientificPaper/submit',
  //   component: NewScientificPaperComponent 
  // },
  // { 
  //   path: 'paperDetailsPage/:processId',
  //   component: PaperDetailsPageComponent 
  // },
  { 
    path: 'subscriptionPayment/:processId',
    component: SubscriptionPaymentComponent 
  },
  // { 
  //   // path: 'coauthor/:num/:processId',
  //   path: 'coauthor/:processId',
  //   component: AddCoauthorComponent
  // },
  // {
  //   path: 'editor/review/:processId',
  //   component: EditorTaskComponent
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
