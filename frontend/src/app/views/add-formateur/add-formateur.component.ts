import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { FormateurService } from 'src/app/shared/services/formateur.service';
import { Formateur } from 'src/app/model/Formateur.model';
import { UserAuthService } from 'src/app/shared/services/user-auth.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';
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
  editingFormateurId: number | null = null;
  isNewFormateur= true;
  constructor(private formBuilder: FormBuilder, private formateurService: FormateurService,
    private userAuthService: UserAuthService,
    private toastr: ToastrService,
    private route:ActivatedRoute
    ) {
    this.formateurForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  ngOnInit(): void {
    console.log("test");
    
    this.route.queryParams.subscribe(params => {
      const formationId = params['id'];
      if (formationId) {
        this.loadFormationDetails(formationId);
        this.editingFormateurId = formationId;
        this.isNewFormateur = false;
      }
    });
  }
  loadFormationDetails(formateurId: number) {
    this.formateurService.getFormateurById(formateurId).subscribe(
      (formateur) => {
        this.formateurForm.patchValue(formateur);
      },
      (error) => {
        console.error('Erreur lors du chargement des détails de la formation :', error);
      }
    );
  }

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
   
    this.formateur.name = formateurBuilder.value.name;
    this.formateur.email = formateurBuilder.value.email;
    this.formateur.password = formateurBuilder.value.password;
    return this.formateur;
  }

}
