import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Formation } from 'src/app/model/formation.model';
import { UserAuthService } from './user-auth.service';
import { HttpParams } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class FormationService {
  private apiUrl = 'http://localhost:8080/form';  // Remplacez par l'URL de votre backend

  constructor(private httpClient: HttpClient, private userAuthService: UserAuthService) { }

  requestHeader = new HttpHeaders({ 'NO-AUTH': 'True' });
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
      formData.append('groupe_seuil', formation.groupe_seuil.toString());
      formData.append('image', imageFile);
  
      const headers = new HttpHeaders({
        'Authorization': 'Bearer ' + jwtToken
      });
  
      return this.httpClient.post<Formation>(`${this.apiUrl}/addFormation/image`, formData, { headers });
    } else {
      console.log('Token JWT non disponible');
    }
  
    return new Observable<Formation>();
  }
  getFormations(): Observable<Formation[]> {
    return this.httpClient.get<Formation[]>(`${this.apiUrl}/getall`);
  }

  getByFilters(categorie: string, ville: string, date: string|null): Observable<Formation[]> {
    const jwtToken = this.userAuthService.getToken();

    let params = new HttpParams();
    if (categorie) {
      params = params.set('categorie', categorie);
    }
    if (ville) {
      params = params.set('ville', ville);
    }
    if (date) {
      params = params.set('date', date);
    }

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + jwtToken
    });

    return this.httpClient.get<Formation[]>(`${this.apiUrl}/getByFilters`, { headers, params });
  }
}
