import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './views/home/home.component';
import { LoginComponent } from './views/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AgrupaMaterialModule } from './material/agrupa-material.module';
import { AdminComponent } from './views/admin/admin.component';
import { FormateurComponent } from './views/formateur/formateur.component';
import { AssistantComponent } from './views/assistant/assistant.component';
import { AddFormateurComponent } from './views/add-formateur/add-formateur.component';
import { ToastrModule } from 'ngx-toastr';
import { FormateurDetailsComponent } from './views/formateur-details/formateur-details.component';
import { ConfirmationDialogComponent } from './views/confirmation-dialog/confirmation-dialog.component';
import { FormationComponent } from './views/formation/formation.component';
import { FormateurEditDialogComponent } from './views/formateur-edit-dialog/formateur-edit-dialog.component';
import { CommonModule } from '@angular/common';
import { FormationdetailsComponent } from './views/formationdetails/formationdetails.component';
import { InscriptionFormComponent } from './views/inscription-form/inscription-form.component';
import { InscriptionformateurexeterneComponent } from './views/inscriptionformateurexeterne/inscriptionformateurexeterne.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    AdminComponent,
    FormateurComponent,
    AssistantComponent,
    AddFormateurComponent,
    FormateurDetailsComponent,
    ConfirmationDialogComponent,
    FormationComponent,
    FormateurEditDialogComponent,
    FormationdetailsComponent,
    InscriptionFormComponent,
    InscriptionformateurexeterneComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    AgrupaMaterialModule,
    CommonModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
   

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
