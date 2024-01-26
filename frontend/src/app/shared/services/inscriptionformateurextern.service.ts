import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InscriptionformateurexternService {
  private baseUrl = 'http://localhost:8080/externe';
  constructor(private httpClient: HttpClient, ) { }
  inscriptionFormateurExtern(name: string, email: string, mots_cles: string): Observable<any> {
    const url = `${this.baseUrl}/inscription/${name}/${email}/${mots_cles}`;
    return this.httpClient.post<any>(url, null);
  }
}
