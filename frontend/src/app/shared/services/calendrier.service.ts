import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserAuthService } from './user-auth.service';
import { Calendrier } from 'src/app/model/Calendrier.modul';

@Injectable({
  providedIn: 'root'
})
export class CalendrierService {
  private API_BASE_URL = "http://localhost:8080";

  constructor(
    private httpClient: HttpClient,
    private userAuthService: UserAuthService
  ) {}

  public getEvents(): Observable<Calendrier[]> {
    const jwtToken = this.userAuthService.getToken();

    if (!jwtToken) {
      console.log('Token JWT non disponible');
      return throwError('Token JWT non disponible');
    }

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + jwtToken,
      'Content-Type': 'application/json'
    });

    const url = `${this.API_BASE_URL}/calendrier/getEvents`;
    return this.httpClient.get<Calendrier[]>(url, { headers })
      .pipe(
        catchError(error => {
          console.error('Erreur lors du chargement des événements :', error);
          return throwError('Erreur lors du chargement des événements');
        })
      );
  }
}
