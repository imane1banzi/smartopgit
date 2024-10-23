import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

export interface OperationDTO {
  titre: string;
  dept: string;
  equipementImpacte : string;
  demandeur : string;
  categorie: string;
  codeOP: string;
  dateDemande : Date;
  datePrevisionelle : Date;
  derniereModification: string;
  commentaire: string;
  enumStatutOP: string;
  matricule: string;
  id : number;

}
export interface CRDTO {
  dateFin: string;
  enumstatutCR: string;
  resume : string;
  equipementImp : string;
  codeOP: string;
  id : number;

}
@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) { }

  getData1(): Observable<CRDTO[]> {
    return this.http.get<CRDTO[]>('http://localhost:8000/SmartOP/listCR');
  }

  getData2(): Observable<OperationDTO[]> {
    return this.http.get<OperationDTO[]>('http://localhost:8000/SmartOP/listOP');
  }
}
