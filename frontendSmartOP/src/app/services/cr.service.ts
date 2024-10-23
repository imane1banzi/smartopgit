import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
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
export class CRService {
  private apiUrl = 'http://localhost:8000/SmartOP/listCR';
  constructor(private http: HttpClient) { }

  getListCR(): Observable<CRDTO[]> {

    return this.http.get<CRDTO[]>(this.apiUrl);
  }
  creerCR(nouveauCR: any) {
    console.log(nouveauCR)
    console.log("Service creer cr calling");

    return this.http.post("http://localhost:8000/SmartOP/nouveauCR"
      , nouveauCR);

  }
  updateCR(updateCR: any, userid : number) {
    console.log("Service update cr calling");
    console.log(updateCR)
    const params = new HttpParams().set('id', userid.toString());
    return this.http.put("http://localhost:8000/SmartOP/updateCR"
      , updateCR, { params });

  }
  supprimerCR(userid : number) {
    console.log("Service update cr calling");

    const params = new HttpParams().set('id', userid.toString());
    return this.http.delete("http://localhost:8000/SmartOP/deleteCR"
      , { params });

  }
}
