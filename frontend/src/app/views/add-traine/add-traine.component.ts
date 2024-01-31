  import { Component, Inject, OnInit } from '@angular/core';
  import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Calendrier } from 'src/app/model/Calendrier.modul';
  import { Entreprise } from 'src/app/model/Entreprise.modul';
  import { Formateur } from 'src/app/model/Formateur.model';
  import { Formation } from 'src/app/model/formation.model';
  import { Groupe } from 'src/app/model/Group.model';
import { CalendrierService } from 'src/app/shared/services/calendrier.service';
  import { EntrepriseService } from 'src/app/shared/services/entreprise.service';
  import { FormateurService } from 'src/app/shared/services/formateur.service';
  import { FormationService } from 'src/app/shared/services/formation.service';
  import { GroupeService } from 'src/app/shared/services/groupe.service';

  @Component({
    selector: 'app-add-traine',
    templateUrl: './add-traine.component.html',
    styleUrls: ['./add-traine.component.css']
  })
  export class AddTraineComponent implements OnInit {

    formations: Formation[] = []; 
    formateurs: Formateur[] = []; 
    entreprises: Entreprise[] = []; 
    groupes: Groupe[] = []; 

    formationName: number = 0;
    formateurName: number = 0;
    entrepriseName: number = 0;
    groupeId: number = 0;
    title:any
    selectedPeriod: any;

    constructor(
      private dialogRef: MatDialogRef<AddTraineComponent>,
      @Inject(MAT_DIALOG_DATA) public data: any,
    private formationService :FormationService,
    private formateurService :FormateurService,
    private entrepriseService:EntrepriseService,
    private groupeService :GroupeService,
    private calendrierService:CalendrierService
    ) {
      this.selectedPeriod = data;
    }

    ngOnInit(): void {
      
      this.loadFormations();
      this.loadFormateurs();
      this.loadEntreprises();
      this.loadGroupes();
    }

    loadFormations(): void {
      this.formationService.getFormations().subscribe(
        (formations: Formation[]) => {
          this.formations = formations;
        },
        (error) => {
          console.error('Erreur lors du chargement des formations :', error);
        }
      );
    }

    loadFormateurs(): void {
    this.formateurService.showFormateurs().subscribe(
    (formateurs:Formateur[])=>{
      this.formateurs = formateurs;
    },
    (error) => {
      console.error('Erreur lors du chargement des formateur :', error);

    }
    )
    }

    loadEntreprises(): void {
      this.entrepriseService.getEntreprises().subscribe(
        (entreprises:Entreprise[])=>{
          this.entreprises=entreprises;
        },
        (error) => {
          console.error('Erreur lors du chargement des entreprise :', error);
      
        }
        )
        }

    loadGroupes(): void {
      console.log("ana hnaaaa");
      
      this.groupeService.getGroupes().subscribe(
        (groupes: Groupe[]) => {
          this.groupes = groupes;
          console.log('Groupes chargés avec succès :', groupes);
        },
        (error) => {
          console.error('Erreur lors du chargement des Groupes :', error);
        }
      );
    }

    onSubmit(): void {
      const selectedFormation = this.formations.find(form => form.id === this.formationName);
      const selectedFormateur = this.formateurs.find(formateur => formateur.id === this.formateurName);
      const selectedEntreprise = this.entreprises.find(entreprise => entreprise.id === this.entrepriseName);
      const selectedGroupe = this.groupes.find(groupe => groupe.id === this.groupeId);
    
      if (selectedFormation && selectedFormateur && selectedEntreprise && selectedGroupe) {
        const newCalendrier: Calendrier = {
          datedebut: this.selectedPeriod.start,
          datefin: this.selectedPeriod.end,
          title: this.title, 
          formation: selectedFormation, 
          formateur: selectedFormateur, 
          entreprise: selectedEntreprise, 
          groupe: selectedGroupe 
        };
    
        if (selectedFormation.id && selectedFormateur.id && selectedEntreprise.id && selectedGroupe.id) {
          this.calendrierService.addTraine(newCalendrier, selectedFormation.id, selectedFormateur.id, selectedEntreprise.id, selectedGroupe.id).subscribe(
            (response: Calendrier) => {
              console.log('Calendrier ajouté avec succès :', response);
            },
            (error) => {
              console.error('Erreur lors de l\'ajout du calendrier :', error);
            });
    
          this.dialogRef.close();
        } else {
          console.error('ID manquant pour la création du calendrier');
        }
      } else {
        console.error('Sélection manquante pour la création du calendrier');
      }
    }
    

    onCancel(): void {
      this.dialogRef.close();
    }
  }
