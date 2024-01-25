import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { InscriptionFormComponent } from '../inscription-form/inscription-form.component';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-formationdetails',
  templateUrl: './formationdetails.component.html',
  styleUrls: ['./formationdetails.component.css']
})
export class FormationdetailsComponent implements OnInit {
  formationId: number | undefined;
  showInscriptionForm: boolean = false;
  inscriptionForm: FormGroup;
  

  constructor(private route: ActivatedRoute, private fb: FormBuilder,private dialog: MatDialog) {
    this.inscriptionForm = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      nde: ['', Validators.required],
      telephone: ['', Validators.required],
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

  submitInscription(): void {
    // Logique pour soumettre le formulaire d'inscription
    console.log('Formulaire soumis avec succès!', this.inscriptionForm.value);
    // Réinitialiser le formulaire après la soumission
    this.inscriptionForm.reset();
    // Masquer le formulaire après la soumission
    this.showInscriptionForm = false;
  }
  openInscriptionForm(): void {
    const dialogRef = this.dialog.open(InscriptionFormComponent, {
      width: '400px', // Définissez la largeur de la popup selon vos besoins
    });
  
    // Vous pouvez ajouter des gestionnaires d'événements ici, par exemple, pour traiter le résultat après la fermeture de la popup.
    dialogRef.afterClosed().subscribe((result: any) => {
      console.log('Popup fermée avec le résultat :', result);
    });

}
}