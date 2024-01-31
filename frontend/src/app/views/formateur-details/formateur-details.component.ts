import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Formateur } from 'src/app/model/Formateur.model';
import { FormateurService } from 'src/app/shared/services/formateur.service';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-formateur-details',
  templateUrl: './formateur-details.component.html',
  styleUrls: ['./formateur-details.component.css']
})
export class FormateurDetailsComponent implements OnInit {
  FormateurDetails :Formateur []=[];
  displayedColumns:String[] = ['id', 'name', 'email','Actions']
  selectedFormateurId: number | null = null;


  constructor(private formateurService: FormateurService, private dialog: MatDialog,
    private router:Router
    ) { }
  ngOnInit(): void {
   this.showFormateur();
  }


  public showFormateur(){
    console.log("gggg");
  this.formateurService.showFormateurs().subscribe(
    (resp:Formateur[])=>{
      console.log("hhhh");
      console.log(resp)
      this.FormateurDetails = resp;
      console.log(this.FormateurDetails);
      
    },(err:HttpErrorResponse)=>{
      console.log(err);
    }
  )
  }

  getFormateurById(id: number): void {
    this.formateurService.getFormateurById(id)
      .subscribe(
        (formateur: Formateur) => {
          console.log('Informations du formateur :', formateur);
          // Faire quelque chose avec les informations récupérées
        },
        (error) => {
          console.error('Erreur lors de la récupération du formateur :', error);
          // Gérer l'erreur
        }
      );
  }

  public deleteFormateur(id:number){
    console.log("delete")
    this.formateurService.deleteFormateurs(id).subscribe(
      () => {
        this.ngOnInit();
        console.log('Formateur supprimé avec succès.');
      },
      (erreur) => {
        console.error('Erreur lors de la suppression du formateur :', erreur);
      }
    );

  }
  public updatformatuer(idfor:number,formateur:Formateur){
    this.formateurService.updateFormateur(idfor,formateur).subscribe(
      ()=>{
        console.log('Formateur modifier avec succès.');
      },
      (erreur) => {
        console.error('Erreur lors de la suppression du formateur :', erreur);
      }
    );
  }
  ouvrirBoiteConfirmation(idFormateur: number): void {
    const dialogRef = this.dialog.open<ConfirmationDialogComponent, { message: string }>(ConfirmationDialogComponent, {
      width: '300px',
      panelClass: 'confirmation-dialog-container',
      data: { message: 'Êtes-vous sûr de vouloir supprimer ce formateur?' }
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        this.deleteFormateur(idFormateur);
      }
    });
}



}

