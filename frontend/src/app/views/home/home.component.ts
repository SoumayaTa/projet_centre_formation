import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Formation } from 'src/app/model/formation.model';
import { FormationService } from 'src/app/shared/services/formation.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  formations: Formation[] = [];
  selectedFormation: Formation | null = null;
  filteredFormations: Formation[] = [];
  search: string = '';
  filterNom: string = '';
  filterVille: string = '';
  filterDate: Date | null = null;
  filterForm: FormGroup;
  villes: string[] = [];
  categories: string[] = [];

  constructor(
    private dialog: MatDialog,
    private formationService: FormationService,
    private router: Router,
    private formBuilder: FormBuilder,
    private datePipe: DatePipe
  ) {
    this.filterForm = this.formBuilder.group({
      categorie: [''],
      ville: [''],
      date: ['']
    });
  }

  ngOnInit(): void {
    this.fetchFormations();
    this.fetchCategoriesAndVilles();
  }

  fetchFormations(): void {
    this.formationService.getFormations().subscribe(
      (formations: Formation[]) => {
        console.log(formations);
        this.formations = formations;
        this.filteredFormations = formations;
      },
      (error: any) => {
        console.error('Erreur lors de la récupération des formations', error);
      }
    );
  }

  fetchCategoriesAndVilles(): void {
    // Récupérer les catégories
    this.formationService.getCategories().subscribe(
      (categories: string[]) => {
        this.categories = categories;
      },
      (error: any) => {
        console.error('Erreur lors de la récupération des catégories', error);
      }
    );

    // Récupérer les villes
    this.formationService.getVilles().subscribe(
      (villes: string[]) => {
        this.villes = villes;
      },
      (error: any) => {
        console.error('Erreur lors de la récupération des villes', error);
      }
    );
  }

  showDetails(formation: Formation): void {
    this.selectedFormation = formation;
  }

  closeDetails(): void {
    this.selectedFormation = null;
  }

  filterFormations(): void {
    const categorie = this.filterForm.get('categorie')?.value || '';
    const ville = this.filterForm.get('ville')?.value || '';
    const dateControl = this.filterForm.get('date');
    // const date = dateControl ? this.datePipe.transform(dateControl.value, 'yyyy-MM-dd') : '';
    this.formationService.getByFilters(categorie, ville).subscribe(
      (formations: Formation[]) => {
        this.filteredFormations = formations;
      },
      (error: any) => {
        console.error('Erreur lors de la récupération des formations filtrées', error);
      }
    );
  }

  navigateToDetailsPage(formation: Formation): void {
    if (formation && formation.id !== undefined) {
      this.router.navigate(['/formation-details', formation.id]);
    }
  }
}
