import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { AddFormateurComponent } from './views/add-formateur/add-formateur.component';
import { AdminComponent } from './views/admin/admin.component';
import { AssistantComponent } from './views/assistant/assistant.component';
import { FormateurDetailsComponent } from './views/formateur-details/formateur-details.component';
import { FormateurComponent } from './views/formateur/formateur.component';
import { HomeComponent } from './views/home/home.component';
import { LoginComponent } from './views/login/login.component';
import { FormationComponent } from './views/formation/formation.component';
import { ShowFormationComponent } from './views/show-formation/show-formation.component';
import { FormationdetailsComponent } from './views/formationdetails/formationdetails.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'admin', component: AdminComponent,canActivate:[AuthGuard],data:{roles:['ROLE_ADMIN']}},
  { path: 'format', component: FormateurComponent,canActivate:[AuthGuard],data:{roles:['ROLE_FORMAT']}},
  { path: 'assistant', component: AssistantComponent,canActivate:[AuthGuard],data:{roles:['ROLE_ASSISTANT']}},
  { path: 'addFormateur', component: AddFormateurComponent,canActivate:[AuthGuard],data:{roles:['ROLE_ADMIN']}},
  { path: 'login', component: LoginComponent},
  { path: 'allFormateur', component: FormateurDetailsComponent,canActivate:[AuthGuard],data:{roles:['ROLE_ADMIN']}},
  { path: 'addformation', component: FormationComponent, canActivate:[AuthGuard], data:{roles:['ROLE_ADMIN']}},
  { path: 'showFormation', component: ShowFormationComponent,canActivate:[AuthGuard],data:{roles:['ROLE_ADMIN']}},
  { path: 'formationdetails', component: FormationdetailsComponent},
  { path: '**', redirectTo: 'home'}    
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
