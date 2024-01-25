// formateur-edit-dialog.component.ts
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Formateur } from 'src/app/model/Formateur.model';

@Component({
  selector: 'app-formateur-edit-dialog',
  templateUrl: './formateur-edit-dialog.component.html',
  styleUrls: ['./formateur-edit-dialog.component.css']
})
export class FormateurEditDialogComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<FormateurEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Formateur
  ) {
    console.log('Données du formateur à éditer :', data);
    console.log('Données du formateur  nom :', data.name);
    
    this.form = this.fb.group({
      id: [data.id, Validators.required],
      name: [data.name, Validators.required],
      email: [data.email, [Validators.required, Validators.email]],
      // Ajoutez d'autres champs du formateur si nécessaire
    });
  }

  save(): void {
    // Implémentez la logique pour enregistrer les modifications ici
    // ...

    this.dialogRef.close(this.form.value);  // Passez les données modifiées à la boîte de dialogue parente
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
