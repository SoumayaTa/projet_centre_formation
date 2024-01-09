import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/userService/user-auth.service';
import { UserService } from '../_services/userService/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm;
  showPassword: boolean = true;
  constructor(private userService: UserService,
    private userAuthService: UserAuthService,
    private router:Router,
    private formBuilder: FormBuilder
    
    ){
      this.loginForm=this.formBuilder.group({
        userName: ['', [Validators.required, Validators.minLength(4)]],
        userPassword: ['', [Validators.required,Validators.maxLength(15),Validators.minLength(6)]],
      
      })

  }


  ngOnInit(): void {
   
  }

}
