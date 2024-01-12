import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  addFormateurForm;
  showPassword: boolean = true;

  constructor(
    private userService: UserService,  // Ajout de l'injection de dépendance
    private router: Router,
    private formBuilder: FormBuilder
  ){
    this.addFormateurForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  ngOnInit(): void {}

  public addUser() {
    console.log(this.userService);
    console.log('Function called');
    const formData = this.addFormateurForm.value;
    console.log(formData);
    this.userService.addUser(formData).subscribe(
      (response: any) => {
        console.log('Utilisateur ajouté avec succès:', response);
        
       
      },
      (err: any) => {
        console.log(err);
      }
    );
  }
}
