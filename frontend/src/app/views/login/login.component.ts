import { Component, OnInit } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { UserAuthService } from "src/app/shared/services/user-auth.service";
import { UserService } from "src/app/shared/services/user.service";

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
