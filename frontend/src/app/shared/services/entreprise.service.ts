import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Entreprise } from 'src/app/model/Entreprise.modul';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class EntrepriseService {
  private apiUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  addEntreprise(entreprise: Entreprise, headers: HttpHeaders): Observable<any> {
    const url = `${this.apiUrl}/entreprise/addNewEntreprise`;
    return this.httpClient.post(url, entreprise, { headers });
  }
  

}
