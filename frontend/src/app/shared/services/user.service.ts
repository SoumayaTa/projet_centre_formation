import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "src/app/model/user.model";
import { UserAuthService } from "./user-auth.service";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private API_BASE_URL = "http://localhost:9095/auth";

  constructor(
    private httpClient: HttpClient,
    private userAuthService: UserAuthService
  ) { }


  requestHeader=new HttpHeaders(
    {'NO-AUTH': 'True'}
  )
  public login(loginData: User) {
    return this.httpClient.post(this.API_BASE_URL + "/generateToken", loginData, { headers: this.requestHeader });
  }


  public forFormateur() {
    return this.httpClient.get(this.API_BASE_URL + '/format/formatProfile', { responseType: "text" });
  }

  public forAdmin() {
    return this.httpClient.get(this.API_BASE_URL + '/admin/adminProfile', { responseType: "text" });
  }

  public forAssistant() {
    return this.httpClient.get(this.API_BASE_URL + '/assistant/assistantProfile', { responseType: "text" });
  }

  // public roleMatch(allowedRoles: string[]): boolean {
  //   const userRoles: string = this.userAuthService.getRoles();
  //   if (userRoles !== '') {
  //     return allowedRoles.some(role => userRoles.includes(role));
  //   }
  //   return false;
  // }
  public roleMatch(allowedRoles: string[]): boolean {
    const userRoles: string = this.userAuthService.getRoles();
  
    if (userRoles !== '') {
      // Divise la chaîne des rôles en un tableau
      const userRolesArray = userRoles.split(',');
  
      // Vérifie si au moins un des rôles autorisés est présent dans le tableau
      return allowedRoles.some(role => userRolesArray.includes(role.trim()));
    }
  
    return false;
  }
  
  
}
