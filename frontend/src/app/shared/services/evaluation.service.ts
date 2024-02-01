import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Evaluation } from 'src/app/model/Evaluation.modul';

@Injectable({
  providedIn: 'root'
})
export class EvaluationService {
  private apiUrl = 'http://localhost:8080/evaluation';

  constructor(private http: HttpClient) { }

  submitEvaluation(evaluationData: Evaluation,id:number): Observable<any> {
    return this.http.post(`${this.apiUrl}/add/${id}`, evaluationData);
  }
}
