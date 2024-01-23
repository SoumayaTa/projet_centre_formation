import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Formation } from 'src/app/model/formation.model';
import { FormationService } from 'src/app/shared/services/formation.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-formation',
  templateUrl: './formation.component.html',
  styleUrls: ['./formation.component.css']
})
export class FormationComponent {
  formationForm: FormGroup;
  formation: Formation = {
    id: 0,
    nom: '',
    nombreHeur: 0,
    cout: 0,
    objectifs: '',
    programme: '',
    categorie: '',
    ville: '',
    date: new Date(),
    imageUrl: ''
  };
  selectedImage: File | null;

  constructor(
    private formBuilder: FormBuilder,
    private formationService: FormationService,
    private toastr: ToastrService
  ) {
    this.selectedImage = null; 
    this.formationForm = this.formBuilder.group({
      nom: ['', [Validators.required, Validators.minLength(3)]],
      nombreHeur: [0, Validators.required],
      cout: [0, Validators.required],
      objectifs: ['', Validators.required],
      programme: ['', Validators.required],
      categorie: ['', Validators.required],
      ville: ['', Validators.required],
      date: ['', Validators.required],
      imageUrl: ['', Validators.required], 
      image: [null, Validators.required]
    });
  }

  addFormation() {
    const formateurData = this.prepareFormData(this.formationForm);
    console.log(formateurData);
    console.log('hywiam');
    console.log('anahona');
    const formationData: Formation = this.formationForm.value;
    console.log('ysf');
    if (this.selectedImage) {
      this.formationService.addFormation(formationData, this.selectedImage).subscribe(
        (response) => {
          console.log('Formation ajoutée avec succès :', response);
          this.formationForm.reset();
          this.toastr.success('Formation a été ajoutée avec succès.', 'Ajout réussi');
        },
        (error) => {
          console.error('Erreur lors de l\'ajout de la formation :', error);
        }
      );
    } else {
      console.error("Image non sélectionnée.");
    }
  }

  prepareFormData(formationBuilder: any): Formation {
    this.formation.nom = formationBuilder.value.nom;
    this.formation.nombreHeur = formationBuilder.value.nombreHeur;
    this.formation.cout = formationBuilder.value.cout;
    this.formation.objectifs = formationBuilder.value.objectifs;
    this.formation.programme = formationBuilder.value.programme;
    this.formation.categorie = formationBuilder.value.categorie;
    this.formation.ville = formationBuilder.value.ville;
    this.formation.date = formationBuilder.value.date;

    return { ...this.formation };
  }

  onImageSelected(event: any) {
    console.log('Image sélectionnée !');
    const file: File = event.target.files[0];
    this.selectedImage = file;
  }
  

}
