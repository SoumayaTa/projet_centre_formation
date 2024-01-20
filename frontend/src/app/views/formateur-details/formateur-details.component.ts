import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Formateur } from 'src/app/model/Formateur.model';
import { FormateurService } from 'src/app/shared/services/formateur.service';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-formateur-details',
  templateUrl: './formateur-details.component.html',
  styleUrls: ['./formateur-details.component.css']
})
export class FormateurDetailsComponent implements OnInit {
  FormateurDetails :Formateur []=[];
  displayedColumns:String[] = ['id', 'name', 'email','Actions']
 

  constructor(private formateurService: FormateurService, private dialog: MatDialog) { }
  ngOnInit(): void {
   this.showFormateur();
  }


  public showFormateur(){
    console.log("gggg");
  this.formateurService.showFormateurs().subscribe(
    (resp:Formateur[])=>{
      console.log("hhhh");
      
      this.FormateurDetails = resp;
      console.log(this.FormateurDetails);
      
    },(err:HttpErrorResponse)=>{
      console.log(err);
    }
  )
  }

  public editFormateurDetails(id:number){
     
    
  }

  public deleteFormateur(id:number){
<<<<<<< HEAD
    this.formateurService.deleteFormateur(id).subscribe(
      (resp)=>{
        this.showFormateur();
      },(err:HttpErrorResponse)=>{
        console.log(err);
      }
    )
=======
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

>>>>>>> a3a275aaacf85a5426c931b6486abcfce22257cf
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

