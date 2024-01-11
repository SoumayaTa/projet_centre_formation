import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UserAuthService } from "./user-auth.service";
import { Observable } from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  constructor(
    private httpClient: HttpClient,
    private http: HttpClient,
    private userAuthService: UserAuthService
  ) { }
  private API_BASE_URL = "http://localhost:9096/auth";
  addUser(user: any): Observable<any> {

    const token = this.userAuthService.getToken();
    
 // Assuming you have a method to get the token

    // Set the token in the request header
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    return this.http.post(`${this.API_BASE_URL}/addNewUser`, user,{headers});
  }

 


  requestHeader=new HttpHeaders(
    {'NO-AUTH': 'True'}
  )
  public login(loginData: any) {
    return this.httpClient.post(this.API_BASE_URL + "/generateToken", loginData,{headers:this.requestHeader});
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
