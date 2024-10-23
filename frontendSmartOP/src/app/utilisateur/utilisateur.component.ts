import {Component, OnInit} from '@angular/core';
import {UtilisateurDTO, UtilisateurService} from "../services/utilisateur.service";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";

@Component({
  selector: 'app-utilisateur',
  templateUrl: './utilisateur.component.html',
  styleUrls: ['./utilisateur.component.css']
})
export class UtilisateurComponent implements OnInit {
  data: UtilisateurDTO[] = [];
  filteredData: UtilisateurDTO[] = [];
  paginatedData: UtilisateurDTO[] = [];
  selectedUser: UtilisateurDTO | null = null;
  userFormGroup!: FormGroup;
  searchMatricule: string = '';
  loginError: string | undefined;
  currentPage: number = 1;
  itemsPerPage: number = 5;
  totalPages: number = 1;
  pages: number[] = [];
  utilisateurId: number = 0;

  passwordFieldType: string = 'password';
  ngOnInit() {

    this.initCompteForm();
    this.loadData();

  }
  constructor(private httpClient:HttpClient, private formBuilder :FormBuilder,
              private utilisateurService:UtilisateurService){

  }
  initCompteForm(){
    this.userFormGroup= this.formBuilder.group(
      {

        nom: this.formBuilder.control("", Validators.required),
        prenom: this.formBuilder.control("", Validators.required),
        password: this.formBuilder.control("", Validators.required),
        adresseMail: this.formBuilder.control("", Validators.required),
        matricule: this.formBuilder.control("", Validators.required),
        enumType_utilisateur: this.formBuilder.control("", Validators.required),
      }
    )

  }
  togglePasswordVisibility() {
    this.passwordFieldType = this.passwordFieldType === 'password' ? 'text' : 'password';
  }
  onSelect(user: UtilisateurDTO) {
    this.selectedUser = user;
    this.utilisateurId = user.id;
    this.userFormGroup.patchValue(user);
  }

  loadData(): void {
    this.utilisateurService.getListUsers().subscribe({
      next: (data: UtilisateurDTO[]) => {
        this.data = data;
        this.filteredData = data;
        this.calculatePagination();
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }

  createUser() {
    const jsonObject = this.userFormGroup.value;
    this.utilisateurService.creeruser(jsonObject).subscribe({
      next: response => {
        console.log("Compte bien créé", response);
        this.selectedUser = null; // Réinitialiser selectedUser après création
        this.userFormGroup.reset(); // Réinitialiser le formulaire
        this.loadData(); // Recharger les données après création
      },
      error: err => {
        console.error("Erreur création utilisateur", err);

      }
    });
  }

  modifyUser() {
    if (this.selectedUser) {
    const jsonObject = { ...this.userFormGroup.value, id: this.utilisateurId };
    this.utilisateurService.updateuser(jsonObject,this.utilisateurId).subscribe({
      next: response => {
        console.log("Utilisateur bien modifié", response);
        this.selectedUser = null; // Réinitialiser selectedUser après modification
        this.userFormGroup.reset(); // Réinitialiser le formulaire
        this.loadData(); // Recharger les données après modification
      },
      error: err => {
        console.error("Erreur modification utilisateur", err);
      }
    });}else {
      console.warn('Aucun utilisateur sélectionné pour la modification');
    }
  }
  deleteUser() {
    if (this.selectedUser) {
      this.utilisateurService.supprimeruser(this.utilisateurId).subscribe({
        next: response => {

          console.log("Utilisateur supprimé", response);
          this.selectedUser = null; // Réinitialiser selectedUser après suppression
          this.userFormGroup.reset(); // Réinitialiser le formulaire
          this.loadData(); // Recharger les données après suppression
        },
        error: err => {

          console.error("Erreur suppression utilisateur", err);
        }
      });
    } else {
      console.warn('Aucun utilisateur sélectionné pour la suppression');
    }
  }

  onSubmit() {
      this.createUser();

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
