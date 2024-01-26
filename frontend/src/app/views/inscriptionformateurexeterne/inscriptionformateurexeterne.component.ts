import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-inscriptionformateurexeterne',
  templateUrl: './inscriptionformateurexeterne.component.html',
  styleUrls: ['./inscriptionformateurexeterne.component.css']
})
export class InscriptionformateurexeterneComponent {
  inscriptionFormateur: FormGroup;
  constructor(
    private dialogRef: MatDialogRef<InscriptionformateurexeterneComponent>,
    private fb: FormBuilder
  ) {
    this.inscriptionFormateur = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      mots_cles: ['', [Validators.required, Validators.minLength(2)]],
     
    });
  }
  ngOnInit(): void {}
  submitInscription(): void {
    console.log("valeur recuprer de la formulaire ",this.inscriptionFormateur.value);
    const formValues = this.inscriptionFormateur.value;
    this.dialogRef.close(formValues);
  }
  cancel(): void {
    this.dialogRef.close();
  }
}
