import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Formateur } from 'src/app/model/Formateur.model';
import { FormateurService } from 'src/app/shared/services/formateur.service';

@Component({
  selector: 'app-formateur-details',
  templateUrl: './formateur-details.component.html',
  styleUrls: ['./formateur-details.component.css']
})
export class FormateurDetailsComponent implements OnInit {
  FormateurDetails :Formateur []=[];
  displayedColumns:String[] = ['id', 'name', 'email','Actions']

  constructor(private formateurService: FormateurService){

  }
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
    this.formateurService.deleteFormateur(id).subscribe(
      (resp)=>{
        this.showFormateur();
      },(err:HttpErrorResponse)=>{
        console.log(err);
      }
    )
  }


}
