import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { EvaluationService } from 'src/app/shared/services/evaluation.service';
import { Evaluation } from 'src/app/model/Evaluation.modul';
import { ToastrService } from 'ngx-toastr';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
@Component({
  selector: 'app-evaluation',
  templateUrl: './evaluation.component.html',
  styleUrls: ['./evaluation.component.css']
})
export class EvaluationComponent implements  OnInit {
  evaluationForm!: FormGroup;
  evaluation: Evaluation = {
    qualite_p: 0,
    rythme: 0,
    scours: 0,
    stp: 0,
    maitrise: 0
  };
 id!: number;
destroy$: Subject<void> = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private evaluationService: EvaluationService,
    private toastr: ToastrService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute
  ) {
    this.initializeForm();
  }
  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.id = params['id'];
      console.log('ID de l\'évaluation récupéré:', this.id);

      // Vous pouvez maintenant utiliser evaluationId dans votre logique
      // Appelez votre service ou effectuez d'autres opérations nécessaires.
    });
  }

  private initializeForm(): void {
    const controlsConfig: { [key: string]: any } = {};
    Object.keys(this.evaluation).forEach(evaluationKey => {
      controlsConfig[evaluationKey] = [0, Validators.required];
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
      console.log(this.id);
      console.log(this.evaluationForm)
      this.evaluationService.submitEvaluation(this.evaluationForm.value,this.id).subscribe(
        () => {
          this.toastr.success('L\'évaluation a été soumise avec succès.', 'Soumission réussie');
          this.snackBar.open('Merci pour votre évaluation!', 'Fermer', {
            duration: 3000
          });
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
