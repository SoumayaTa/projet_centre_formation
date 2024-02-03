import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { GroupeService } from 'src/app/shared/services/groupe.service';

@Component({
  selector: 'app-group',
  templateUrl: 'group.component.html',
  styleUrls: ['group.component.css']
})
export class GroupComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private groupeService: GroupeService,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<GroupComponent>
  ) {}

  closePopup(): void {
    this.dialogRef.close();
  }
  
  sendFeedback(formation: any, groupe: any): void {
    this.groupeService.sendFeedbackEmail(groupe.id).subscribe(
      (response) => {
        console.log('Feedback envoyé avec succès', response);
        
      },
      (error) => {
        console.error('Error sending feedback:', error);
        this.toastr.success('Feedback envoyé avec succès.', 'Envoie réussie');
      }
    );
  }
}
