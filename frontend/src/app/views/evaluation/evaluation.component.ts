import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { EvaluationService } from 'src/app/shared/services/evaluation.service'; // Assurez-vous de spécifier le chemin correct
import { Evaluation } from 'src/app/model/Evaluation.modul';
import { ToastrService } from 'ngx-toastr';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-evaluation',
  templateUrl: './evaluation.component.html',
  styleUrls: ['./evaluation.component.css']
})
export class EvaluationComponent {
  evaluationForm!: FormGroup;
  evaluation: Evaluation = {
    qualite_p: 0,
    rythme: 0,
    scours: 0,
    stp: 0,
    maitrise: 0
  };

  constructor(private fb: FormBuilder, private evaluationService: EvaluationService,
     private toastr: ToastrService, private snackBar: MatSnackBar,
     ) {
    this.initializeForm();
  }

  private initializeForm(): void {
    const controlsConfig: { [key: string]: any } = {};
    Object.keys(this.evaluation).forEach(evaluation => {
      controlsConfig[evaluation] = [0, Validators.required];
    });
    this.evaluationForm = this.fb.group(controlsConfig);
  }
  setRating(controlName: string, rating: number): void {
    const control = this.evaluationForm.get(controlName) as FormControl;
  
    if (control) {
      control.setValue(rating);
    }
  }

  submitForm(): void {
    if (this.evaluationForm.valid) {
       this.evaluationService.submitEvaluation(this.evaluationForm.value).subscribe(
        response => {
          this.toastr.success('L\'évaluation a été soumise avec succès.', 'Soumission réussie');
          // this.snackBar.open('Merci pour votre évaluation!', 'Fermer',{
          //   duration: 3000
          // });
          console.log('Évaluation soumise avec succès', response);
        },
        error => {
          console.error('Erreur lors de la soumission de l\'évaluation', error);
        }
      );
    }
  }
  
  getKeys(obj: any): string[] {
    return Object.keys(obj);
  }
    
}
