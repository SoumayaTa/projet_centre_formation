import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EvaluationService {
  private apiUrl = 'http://localhost:8080/evaluation';

  constructor(private http: HttpClient) { }

  submitEvaluation(evaluationData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/add`, evaluationData);
  }
}
