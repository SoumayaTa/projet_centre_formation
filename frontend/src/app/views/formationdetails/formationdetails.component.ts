import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { InscriptionFormComponent } from '../inscription-form/inscription-form.component';
import { MatDialog } from '@angular/material/dialog';
import { Individus } from 'src/app/model/Individus.model';
import { IndividusService } from 'src/app/shared/services/individuals.service';
@Component({
  selector: 'app-formationdetails',
  templateUrl: './formationdetails.component.html',
  styleUrls: ['./formationdetails.component.css']
})
export class FormationdetailsComponent implements OnInit {
  formationId: number | undefined;
  showInscriptionForm: boolean = false;
  inscriptionForm: FormGroup;
  individu: Individus = {
    nom: '',
    prenom: '',
    dateNaissance: new Date(),
    ville: '',
    email: '',
    telephone: '',
   
  };


  constructor(private route: ActivatedRoute, private individusService: IndividusService,private fb: FormBuilder,private dialog: MatDialog) {
    this.inscriptionForm = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      ville: ['', Validators.required],
      telephone: ['', Validators.required],
      dateNaissance: [null, Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.formationId = params['id'];
    });
  }

  toggleInscriptionForm(): void {
    this.showInscriptionForm = !this.showInscriptionForm;
  }

  
  inscription(formationId: number): void {
    console.log('Date de naissance avant l\'appel au service:', this.individu);
    const seuilTotalIndividusParGroupe = 3;
    this.individusService.inscription(this.individu, formationId).subscribe(
      (result) => {
        // Traitement du résultat si nécessaire
        console.log('Inscription réussie:', result);
      },
      (error) => {
        // Traitement de l'erreur si nécessaire
        console.error('Erreur lors de l\'inscription:', error);
      }
    );
  }
  openInscriptionForm(formationId: number): void {
    const dialogRef = this.dialog.open(InscriptionFormComponent, {
      width: '400px', // Définissez la largeur de la popup selon vos besoins
    });
  
    // Vous pouvez ajouter des gestionnaires d'événements ici, par exemple, pour traiter le résultat après la fermeture de la popup.
    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        this.individu = result;
        this.inscription(formationId);
        
      }
    });
}
}