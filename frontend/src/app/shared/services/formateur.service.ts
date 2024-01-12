import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Formateur } from 'src/app/model/Formateur.model';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class FormateurService {
  private API_BASE_URL = "http://localhost:9095/auth";
  constructor(private httpClient: HttpClient,
    private userAuthService: UserAuthService) { }

  requestHeader = new HttpHeaders(
    { 'NO-AUTH': 'True' }
  );

  public addFormateur(formateur: Formateur): Observable<any> {
    const jwtToken = this.userAuthService.getToken();
    if (jwtToken) {
      const headers = new HttpHeaders({
        'Authorization': 'Bearer ' + jwtToken,
        'Content-Type': 'application/json'
      });
      
      return this.httpClient.post(`${this.API_BASE_URL}/addNewUser`, formateur, { headers });
    } else {
      
      console.log('Token JWT non disponible');
      
    }
   return new Observable<any>();
  }

  public getFormateurProfile(): Observable<any> {
    return this.httpClient.get(`${this.API_BASE_URL}/format/formatProfile`, { responseType: "text" });
  }



}
