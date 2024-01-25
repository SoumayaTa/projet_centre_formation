import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-inscription-form',
  templateUrl: './inscription-form.component.html',
  styleUrls: ['./inscription-form.component.css']
})
export class InscriptionFormComponent implements OnInit {
  inscriptionForm: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<InscriptionFormComponent>,
    private fb: FormBuilder
  ) {
    this.inscriptionForm = this.fb.group({
      // ... définissez les champs du formulaire avec les validateurs nécessaires
    });
  }
  ngOnInit(): void {}
  submitInscription(): void {
   
    this.dialogRef.close(/* envoyer un résultat si nécessaire */);
  }
  cancel(): void {
    this.dialogRef.close();
  }
}
