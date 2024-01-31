import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Entreprise } from 'src/app/model/Entreprise.modul';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class EntrepriseService {
  private apiUrl = 'http://localhost:8080';  // Mettez l'URL de votre API ici

  constructor(private httpClient: HttpClient,
    private userAuthService: UserAuthService
    ) { }

  addEntreprise(entreprise: Entreprise, headers: HttpHeaders): Observable<any> {
    const url = `${this.apiUrl}/entreprise/addNewEntreprise`;
    return this.httpClient.post(url, entreprise, { headers });
  }
  getEntreprises():Observable<Entreprise[]>{
    const jwtToken = this.userAuthService.getToken();
    if (jwtToken) {
      const headers = new HttpHeaders({
        'Authorization': 'Bearer ' + jwtToken,

        'Content-Type': 'application/json'
      });
    return this.httpClient.get<Entreprise[]>(`${this.apiUrl}/entreprise/getallEntreprise`);
    }else{
      console.log('Token JWT non disponible');
    }
    return new Observable<any>();
   }

}
