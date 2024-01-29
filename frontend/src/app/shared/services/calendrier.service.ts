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

  public addNewCalendar(calendrier: Calendrier, formationId: number, formateurId: number, entrepriseId: number,groupeId:number): Observable<Calendrier> {
    const jwtToken = this.userAuthService.getToken();

    if (!jwtToken) {
      console.log('Token JWT non disponible');
      return throwError('Token JWT non disponible');
    }

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + jwtToken,
      'Content-Type': 'application/json'
    });

    const url = `${this.API_BASE_URL}/calendrier/addnewCalendar/${formationId}/${formateurId}/${entrepriseId}/${groupeId}`;
    return this.httpClient.post<Calendrier>(url, calendrier, { headers })
      .pipe(
        catchError(error => {
          console.error('Erreur lors de l\'ajout du calendrier :', error);
          return throwError('Erreur lors de l\'ajout du calendrier');
        })
      );
  }

  public getEvents(): Observable<any[]> {
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
    return this.httpClient.get<any[]>(url, { headers })
      .pipe(
        catchError(error => {
          console.error('Erreur lors du chargement des événements :', error);
          return throwError('Erreur lors du chargement des événements');
        })
      );
  }
}