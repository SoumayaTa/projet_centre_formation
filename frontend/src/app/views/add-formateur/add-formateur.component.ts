import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { FormateurService } from 'src/app/shared/services/formateur.service';
import { Formateur } from 'src/app/model/Formateur.model';
import { UserAuthService } from 'src/app/shared/services/user-auth.service';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-add-formateur',
  templateUrl: './add-formateur.component.html',
  styleUrls: ['./add-formateur.component.css']
})
export class AddFormateurComponent implements OnInit {
  formateurForm;
  formateur: Formateur ={
    id: 0,
    name: '',
    password: '',
    email: '',
    mots_cles:''
  }

  constructor(private formBuilder: FormBuilder, private formateurService: FormateurService,
    private userAuthService: UserAuthService,
    private toastr: ToastrService,
    ) {
    this.formateurForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  ngOnInit(): void {}

  addFormateur() {
    const formateurData = this.prepareFormData(this.formateurForm);
  
   console.log(formateurData);
   
   this.toastr.success('Le formateur a été ajouté avec succès.', 'Ajout réussi');
      this.formateurService.addFormateur(formateurData).subscribe(
        (response: Formateur) => {
          console.log(response);
          this.formateurForm.reset();
        },
        (error: HttpErrorResponse) => {
          console.log(error);
        }
      );
    
  }
  

  prepareFormData(formateurBuilder: any): Formateur {
    // formateur: Formateur = {
    //   name: '',
    //   password: '',
    //   email: ''
    // };
    this.formateur.name = formateurBuilder.value.name;
    this.formateur.email = formateurBuilder.value.email;
    this.formateur.password = formateurBuilder.value.password;
    return this.formateur;
  }

}
