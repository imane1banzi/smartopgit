import { Component } from '@angular/core';
import {OperationDTO, OperationService} from "../services/operation.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {CRDTO, CRService} from "../services/cr.service";
import * as Papa from 'papaparse';

@Component({
  selector: 'app-cr',
  templateUrl: './cr.component.html',
  styleUrls: ['./cr.component.css']
})
export class CRComponent {
  data: CRDTO[] = [];
  filteredData: CRDTO[] = [];
  paginatedData: CRDTO[] = [];
  selectedUser: CRDTO | null = null;
  userFormGroup!: FormGroup;
  searchMatricule: string = '';
  searchcodeOP: string = '';
  currentPage: number = 1;
  itemsPerPage: number = 5;
  totalPages: number = 1;
  pages: number[] = [];
  utilisateurId: number = 0;

  passwordFieldType: string = 'password';
  searchQuery: string = '';
  ngOnInit() {

    this.initCompteForm();
    this.loadData();

  }
  constructor(private httpClient:HttpClient, private formBuilder :FormBuilder,
              private crservice :CRService){

  }
  initCompteForm(){
    this.userFormGroup= this.formBuilder.group(
      {

        dateFin: this.formBuilder.control("", Validators.required),
        enumstatutCR: this.formBuilder.control("", Validators.required),
        resume: this.formBuilder.control("", Validators.required),
        equipementImp: this.formBuilder.control("", Validators.required),
        codeOP: this.formBuilder.control("", Validators.required),
      }
    )

  }
  togglePasswordVisibility() {
    this.passwordFieldType = this.passwordFieldType === 'password' ? 'text' : 'password';
  }
  onSelect(user: CRDTO) {
    this.selectedUser = user;
    this.utilisateurId = user.id;
    this.userFormGroup.patchValue(user);
  }

  loadData(): void {
    this.crservice.getListCR().subscribe({
      next: (data: CRDTO[]) => {
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
      'Date Fin': new Date(row.dateFin).toLocaleDateString('fr-FR'),
      'Statut CR': row.enumstatutCR,
      'Résumé': row.resume,
      'Equipement Impactée': row.equipementImp,
      'Code Opération': row.codeOP
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
    this.crservice.creerCR(jsonObject).subscribe({
      next: response => {
        console.log("CR bien créé", response);
        this.selectedUser = null; // Réinitialiser selectedUser après création
        this.userFormGroup.reset(); // Réinitialiser le formulaire
        this.loadData(); // Recharger les données après création
      },
      error: err => {
        console.error("Erreur création CR", err);
      }
    });
  }

  modifyUser() {
    if (this.selectedUser) {
      const jsonObject = { ...this.userFormGroup.value, id: this.utilisateurId };
      this.crservice.updateCR(jsonObject,this.utilisateurId).subscribe({
        next: response => {
          console.log("CR bien modifié", response);
          this.selectedUser = null; // Réinitialiser selectedUser après modification
          this.userFormGroup.reset(); // Réinitialiser le formulaire
          this.loadData(); // Recharger les données après modification
        },
        error: err => {
          console.error("Erreur modification CR", err);
        }
      });}else {
      console.warn('Aucun CR sélectionné pour la modification');
    }
  }
  deleteUser() {
    if (this.selectedUser) {
      this.crservice.supprimerCR(this.utilisateurId).subscribe({
        next: response => {

          console.log("CR supprimé", response);
          this.selectedUser = null; // Réinitialiser selectedUser après suppression
          this.userFormGroup.reset(); // Réinitialiser le formulaire
          this.loadData(); // Recharger les données après suppression
        },
        error: err => {

          console.error("Erreur suppression CR", err);
        }
      });
    } else {
      console.warn('Aucun CR sélectionné pour la suppression');
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
  onCombinedSearch() {
    const query = this.searchQuery.toLowerCase();
    this.filteredData = this.data.filter(user =>
      user.enumstatutCR.toLowerCase().includes(query) ||
      user.codeOP.toLowerCase().includes(query) ||
      user.equipementImp.toLowerCase().includes(query)

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
