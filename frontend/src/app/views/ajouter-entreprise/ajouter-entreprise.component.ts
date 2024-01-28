import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Entreprise } from 'src/app/model/Entreprise.modul';
import { EntrepriseService } from 'src/app/shared/services/entreprise.service';


@Component({
  selector: 'app-ajouter-entreprise',
  templateUrl: './ajouter-entreprise.component.html',
  styleUrls: ['./ajouter-entreprise.component.css']
})
export class AjouterEntrepriseComponent {
  entrepriseForm: FormGroup;
  entreprise: Entreprise = {
    name: '',
    address: '',
    phoneNumber: '',
    email: '',
    url: ''
  };

  constructor(
    private formBuilder: FormBuilder,
    private entrepriseService: EntrepriseService,
    private toastr: ToastrService
  ) {
    this.entrepriseForm = this.formBuilder.group({
      name: ['', Validators.required],
      address: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      url: ['', Validators.required]
    });
  }

  addEntreprise() {
    const entrepriseData = this.prepareFormData(this.entrepriseForm);

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Proxy': 'http://localhost:8080'  
    });

    this.toastr.success('Le entreprise a été ajouté avec succès.', 'Ajout réussi');

    this.entrepriseService.addEntreprise(entrepriseData, headers).subscribe(
      (response: Entreprise) => {
        console.log(response);
        this.entrepriseForm.reset();
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  prepareFormData(entrepriseBuilder: any): Entreprise {
    this.entreprise.name = entrepriseBuilder.value.name;
    this.entreprise.address = entrepriseBuilder.value.address;
    this.entreprise.email = entrepriseBuilder.value.email;
    this.entreprise.phoneNumber = entrepriseBuilder.value.phoneNumber;
    this.entreprise.url = entrepriseBuilder.value.url;
    return this.entreprise;
  }
}
