import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Inscription } from 'src/app/model/inscription.model';
import { InscriptionformateurexternService } from 'src/app/shared/services/inscriptionformateurextern.service';

@Component({
  selector: 'app-inscriptionformateurexterne',
  templateUrl: './inscriptionformateurexeterne.component.html',
  styleUrls: ['./inscriptionformateurexeterne.component.css']
})
export class InscriptionformateurexeterneComponent implements OnInit {
  formateurexterne: Inscription = {
    name: '',
    email: '',
    mots_cles: '',
    date: new Date()
  };
  inscriptionFormateur: FormGroup;

  constructor(
    private fb: FormBuilder,
    private inscriptionforservice: InscriptionformateurexternService,
   
  ) {
    this.inscriptionFormateur = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      mots_cles: ['', [Validators.required, Validators.minLength(2)]],
    });
  }

  ngOnInit(): void {}

  inscriotionformateurexterne(): void {
    if (this.inscriptionFormateur.valid) {
      const { name, email, mots_cles } = this.inscriptionFormateur.value;
      this.inscriptionforservice.inscriptionFormateurExtern(name, email, mots_cles)
        .subscribe(
          (result) => {
            // Traitement du résultat si nécessaire
            console.log('Inscription réussie:', result);
            // Fermer la boîte de dialogue ou effectuer une autre action après inscription réussie
           
          },
          (error) => {
            // Traitement de l'erreur si nécessaire
            console.error('Erreur lors de l\'inscription:', error);
          }
        );
    }
  }

 
}
