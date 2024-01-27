import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Formation } from 'src/app/model/formation.model';
import { FormationService } from 'src/app/shared/services/formation.service';
import { Router } from '@angular/router';
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
  router: any;

  constructor(private dialog: MatDialog, private formationService: FormationService) {}

  ngOnInit(): void {
    this.fetchFormations();
  }

  fetchFormations(): void {
    this.formationService.getFormations().subscribe(
      (formations: Formation[]) => {
        console.log(formations)
        this.formations = formations;
        this.filteredFormations = formations;
      },
      (error: any) => {
        console.error('Erreur lors de la récupération des formations', error);
      }
    );
  }

  showDetails(formation: Formation): void {
    this.selectedFormation = formation;
  }

  closeDetails(): void {
    this.selectedFormation = null;
  }

  updateFilteredFormations(): void {
    // Filtrer les formations en fonction de la valeur de recherche
    this.filteredFormations = this.formations.filter(formation =>
      formation.nom.toLowerCase().includes(this.search.toLowerCase()) ||
      formation.ville.toLowerCase().includes(this.search.toLowerCase())
    );
  }
  navigateToDetailsPage(formation: Formation): void {
    if (formation && formation.id !== undefined) {
      this.router.navigate(['/formation-details', formation.id]);
    }
  }
  
}
