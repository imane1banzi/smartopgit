import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
export interface UtilisateurDTO {
  nom: string;
  prenom: string;
  adresseMail: string;
  matricule : string;
  enumType_utilisateur: string;
  id : number;
}
@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {

  private apiUrl = 'http://localhost:8000/SmartOP/listUsers';
  constructor(private http: HttpClient) { }

  getListUsers(): Observable<UtilisateurDTO[]> {

    return this.http.get<UtilisateurDTO[]>(this.apiUrl);
  }
  creeruser(nouveauuser: any) {
    console.log("Service creer user calling");

    return this.http.post("http://localhost:8000/SmartOP/nouveauUser"
      , nouveauuser);

  }
  updateuser(updateuser: any, userid : number) {
    console.log("Service update user calling");
    console.log(updateuser)
    const params = new HttpParams().set('id', userid.toString());
    return this.http.put("http://localhost:8000/SmartOP/updateUser"
      , updateuser, { params });

  }
  supprimeruser(userid : number) {
    console.log("Service update user calling");

    const params = new HttpParams().set('id', userid.toString());
    return this.http.delete("http://localhost:8000/SmartOP/deleteUser"
      , { params });

  }
}
