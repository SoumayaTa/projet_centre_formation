import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Formation } from 'src/app/model/formation.model';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class FormationService {
  private apiUrl = 'http://localhost:8080/form';  // Remplacez par l'URL de votre backend

  constructor(private httpClient: HttpClient, private userAuthService: UserAuthService) { }

  requestHeader = new HttpHeaders({ 'NO-AUTH': 'True' });

  // addFormation(formation: Formation): Observable<Formation> {
  //   const jwtToken = this.userAuthService.getToken();
    
  //   if (jwtToken) {
  //     const headers = new HttpHeaders({
  //       'Authorization': 'Bearer ' + jwtToken,
  //       'Content-Type': 'application/json'
  //     });
      
  //     return this.httpClient.post<Formation>(`${this.apiUrl}/formation/addFormation`, formation, { headers });
  //   } else {
  //     console.log('Token JWT non disponible');
  //   }
    
  //   return new Observable<Formation>();
  // }

  addFormation(formation: Formation, imageFile: File): Observable<Formation> {
    const jwtToken = this.userAuthService.getToken();
  
    if (jwtToken) {
      const formData = new FormData();
      formData.append('nom', formation.nom);
      formData.append('nombreHeur', formation.nombreHeur.toString());
      formData.append('cout', formation.cout.toString());
      formData.append('objectifs', formation.objectifs);
      formData.append('programme', formation.programme);
      formData.append('categorie', formation.categorie);
      formData.append('ville', formation.ville);
      formData.append('image', imageFile);
  
      const headers = new HttpHeaders({
        'Authorization': 'Bearer ' + jwtToken
      });
  
      return this.httpClient.post<Formation>(`${this.apiUrl}/formation/addFormation/image`, formData, { headers });
    } else {
      console.log('Token JWT non disponible');
    }
  
    return new Observable<Formation>();
  }
  
}
