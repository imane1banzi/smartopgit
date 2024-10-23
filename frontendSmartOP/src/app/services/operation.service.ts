import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
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
@Injectable({
  providedIn: 'root'
})
export class OperationService {

  private apiUrl = 'http://localhost:8000/SmartOP/listOP';
  constructor(private http: HttpClient) { }

  getListOP(): Observable<OperationDTO[]> {

    return this.http.get<OperationDTO[]>(this.apiUrl);
  }
  creerOP(nouvelleOP: any) {
    console.log(nouvelleOP)
    console.log("Service creer OP calling");

    return this.http.post("http://localhost:8000/SmartOP/nouvelleOP"
      , nouvelleOP);

  }
  updateOP(updateOP: any, userid : number) {
    console.log("Service update OP calling");
    console.log(updateOP)
    const params = new HttpParams().set('id', userid.toString());
    return this.http.put("http://localhost:8000/SmartOP/updateOP"
      , updateOP, { params });

  }
  supprimerOP(userid : number) {
    console.log("Service update OP calling");

    const params = new HttpParams().set('id', userid.toString());
    return this.http.delete("http://localhost:8000/SmartOP/deleteOP"
      , { params });

  }
}
