import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {CRComponent} from "./cr/cr.component";
import {OperationComponent} from "./operation/operation.component";
import {UtilisateurComponent} from "./utilisateur/utilisateur.component";
import {DashboardComponent} from "./dashboard/dashboard.component";

const routes: Routes = [
  { path: 'home', component: HomeComponent,
      children :[
        {path:'cr',component : CRComponent},
        {path:'operation',component : OperationComponent},
        {path:'utilisateur',component : UtilisateurComponent},
        {path:'dashboard',component : DashboardComponent}
]

  },
  { path: 'login', component: LoginComponent },
  {path:'',component :LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
