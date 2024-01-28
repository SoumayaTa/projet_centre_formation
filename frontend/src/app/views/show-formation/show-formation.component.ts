// show-formation.component.ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Formation } from 'src/app/model/formation.model';
import { FormationService } from 'src/app/shared/services/formation.service';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { ImageDialogComponent } from '../image-dialog/image-dialog.component';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-show-formation',
  templateUrl: './show-formation.component.html',
  styleUrls: ['./show-formation.component.css']
})
export class ShowFormationComponent implements OnInit {
  formationDetails: Formation[] = [];
  displayedColumns: string[] = ['id', 'nom', 'nombreHeur', 'cout', 'objectifs','programme','categorie','ville', 'Actions'];
  selectedFormationId: number | null = null;
  showTable = false;
  showMoreFormation = false;
  pageNumber = 0;
   

  totalItems = 0;
  itemsPerPage = 5; 
  @ViewChild(MatPaginator) paginator!: MatPaginator;


  constructor(
    private formationService: FormationService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.showFormations();
  }

  public searchByKeyWord(searchKey: string) {
    console.log(searchKey);
    this.pageNumber = 0;
    this.formationDetails = [];
    this.showFormations(searchKey);
  }

  public showFormations(searchKey: string = "") {
    console.log("starting...");
    this.showTable = false;
  
    this.formationService.showFormation(this.pageNumber, this.itemsPerPage, searchKey).subscribe(
      (resp: any) => {
        console.log("loading formations");
        this.formationDetails = resp;  // Modifier ici
        this.showTable = true;
        this.totalItems = resp.length;  // Modifier ici
        console.log(this.formationDetails);
      },
      (err: HttpErrorResponse) => {
        console.log(err);
      }
    );
  }
  

  public editFormationDetails(id: number) {
    this.selectedFormationId = id;
    this.router.navigate(['/addformation'], { queryParams: { id: id } });
  }

  viewImage(id: number, photos: string) {
    console.log('URL de l\'image :', photos);

    if (id) {
      this.formationService.getFormationById(id).subscribe(
        (formation: Formation) => {
          this.dialog.open(ImageDialogComponent, {
            data: { imageUrl: photos },
            width: '50%',
          });
        },
        (error) => {
          console.error('Erreur lors de la récupération de l\'image :', error);
        }
      );
    } else {
      console.error('ID non défini.');
    }
  }

  public delete(id: number) {
    console.log('starting deleting ...');

    this.formationService.deleteFormation(id).subscribe(
      () => {
        console.log('deleting the formation');
        this.ngOnInit();
        console.log('Formation supprimé avec succès.');
        console.log(this.formationDetails);
      }, (err: HttpErrorResponse) => {
        console.log(err);
      }
    );
    this.ngOnInit();
  }

  ouvrirBoiteConfirmation(idFormation: number): void {
    const dialogRef = this.dialog.open<ConfirmationDialogComponent, { message: string }>(ConfirmationDialogComponent, {
      width: '300px',
      panelClass: 'confirmation-dialog-container',
      data: { message: 'Êtes-vous sûr de vouloir supprimer cette formation?' }
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        this.delete(idFormation);

      }
    });
  }

  loadPage(event: PageEvent): void {
    this.pageNumber = event.pageIndex;
    this.itemsPerPage = event.pageSize;
    this.showFormations();
  }

}
