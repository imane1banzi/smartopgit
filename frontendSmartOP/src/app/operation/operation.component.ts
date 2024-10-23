import { Component } from '@angular/core';

import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {OperationDTO, OperationService} from "../services/operation.service";
import * as Papa from 'papaparse';

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.css']
})
export class OperationComponent {
  data: OperationDTO[] = [];
  filteredData: OperationDTO[] = [];
  paginatedData: OperationDTO[] = [];
  selectedUser: OperationDTO | null = null;
  userFormGroup!: FormGroup;
  searchMatricule: string = '';
  searchcodeOP: string = '';
  currentPage: number = 1;
  itemsPerPage: number = 5;
  totalPages: number = 1;
  pages: number[] = [];
  utilisateurId: number = 0;
  loginError: string | undefined;

  passwordFieldType: string = 'password';
  searchQuery: string = '';
  ngOnInit() {

    this.initCompteForm();
    this.loadData();

  }
  constructor(private httpClient:HttpClient, private formBuilder :FormBuilder,
              private operationservice :OperationService){

  }
  initCompteForm(){
    this.userFormGroup= this.formBuilder.group(
      {

        titre: this.formBuilder.control("", Validators.required),
        dept: this.formBuilder.control("", Validators.required),
        equipementImpacte: this.formBuilder.control("", Validators.required),
        demandeur: this.formBuilder.control("", Validators.required),
        categorie: this.formBuilder.control("", Validators.required),
        codeOP: this.formBuilder.control("", Validators.required),
        dateDemande: this.formBuilder.control("", Validators.required),
        datePrevisionelle: this.formBuilder.control("", Validators.required),
        derniereModification: this.formBuilder.control("", Validators.required),
        commentaire: this.formBuilder.control("", Validators.required),
        enumStatutOP: this.formBuilder.control("", Validators.required),
        matricule: this.formBuilder.control("", Validators.required),
      }
    )

  }
  togglePasswordVisibility() {
    this.passwordFieldType = this.passwordFieldType === 'password' ? 'text' : 'password';
  }
  onSelect(user: OperationDTO) {
    this.selectedUser = user;
    this.utilisateurId = user.id;
    this.userFormGroup.patchValue(user);
  }

  loadData(): void {
    this.operationservice.getListOP().subscribe({
      next: (data: OperationDTO[]) => {
        this.data = data;
        this.filteredData = data;
        this.calculatePagination();
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }
  exportToCSV() {
    const csvData = this.paginatedData.map(row => ({
      Titre: row.titre,
      Dept: row.dept,
      EquipementOP: row.equipementImpacte,
      Demandeur: row.demandeur,
      Categorie: row.categorie,
      CodeOP: row.codeOP,
      DateDemande: new Date(row.dateDemande).toLocaleDateString('fr-FR'),
      DatePrevisionelle: new Date(row.datePrevisionelle).toLocaleDateString('fr-FR'),
      DerniereModification: row.derniereModification,
      Commentaire: row.commentaire,
      Statut: row.enumStatutOP,
      Matricule: row.matricule
    }));

    const csv = Papa.unparse(csvData);

    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    const url = URL.createObjectURL(blob);
    link.setAttribute('href', url);
    link.setAttribute('download', 'table-data.csv');
    link.style.visibility = 'hidden';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

  createOP() {
    const jsonObject = this.userFormGroup.value;
    console.log(this.userFormGroup.value)
    this.operationservice.creerOP(jsonObject).subscribe({
      next: response => {
        console.log("OP bien créé", response);
        this.selectedUser = null; // Réinitialiser selectedUser après création
        this.userFormGroup.reset(); // Réinitialiser le formulaire
        this.loadData(); // Recharger les données après création
      },
      error: err => {
        console.error("Erreur création OP", err);

      }
    });
  }

  modifyUser() {
    if (this.selectedUser) {
      const jsonObject = { ...this.userFormGroup.value, id: this.utilisateurId };
      this.operationservice.updateOP(jsonObject,this.utilisateurId).subscribe({
        next: response => {
          console.log("OP bien modifié", response);
          this.selectedUser = null; // Réinitialiser selectedUser après modification
          this.userFormGroup.reset(); // Réinitialiser le formulaire
          this.loadData(); // Recharger les données après modification
        },
        error: err => {
          console.error("Erreur modification OP", err);
        }
      });}else {
      console.warn('Aucune OP sélectionné pour la modification');
    }
  }
  deleteUser() {
    if (this.selectedUser) {
      this.operationservice.supprimerOP(this.utilisateurId).subscribe({
        next: response => {

          console.log("OP supprimé", response);
          this.selectedUser = null; // Réinitialiser selectedUser après suppression
          this.userFormGroup.reset(); // Réinitialiser le formulaire
          this.loadData(); // Recharger les données après suppression
        },
        error: err => {

          console.error("Erreur suppression OP", err);
        }
      });
    } else {
      console.warn('Aucune OP sélectionné pour la suppression');
    }
  }

  onSubmit() {
    this.createOP();

  }

  onModify() {
    this.modifyUser();

  }
  onDelete() {
    this.deleteUser();
  }
  onSearch() {
    this.filteredData = this.data.filter(user => user.matricule.includes(this.searchMatricule));
    this.calculatePagination();
  }
  onSearchcode() {
    this.filteredData = this.data.filter(user => user.codeOP.includes(this.searchcodeOP));
    this.calculatePagination();
  }
  onCombinedSearch() {
    const query = this.searchQuery.toLowerCase();
    this.filteredData = this.data.filter(user =>
      user.matricule.toLowerCase().includes(query) ||
      user.codeOP.toLowerCase().includes(query) ||
      user.demandeur.toLowerCase().includes(query) ||
      user.categorie.toLowerCase().includes(query)
    );
    this.calculatePagination();
  }
  calculatePagination() {
    this.totalPages = Math.ceil(this.filteredData.length / this.itemsPerPage);
    this.pages = Array(this.totalPages).fill(0).map((x, i) => i + 1);
    this.changePage(1);
  }

  changePage(page: number) {
    if (page < 1 || page > this.totalPages) {
      return;
    }
    this.currentPage = page;
    const startIndex = (page - 1) * this.itemsPerPage;
    const endIndex = Math.min(startIndex + this.itemsPerPage, this.filteredData.length);
    this.paginatedData = this.filteredData.slice(startIndex, endIndex);
  }
}
