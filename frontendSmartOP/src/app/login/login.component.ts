import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string | undefined;
  password: string | undefined;
  loginError: string | undefined;

  constructor(private router: Router) {}

  login() {
    // Simuler une vérification de l'authentification
    if (this.username === 'user' && this.password === 'password') {
      // Authentification réussie, naviguer vers la page d'accueil
      this.router.navigate(['home']);
    } else {
      this.loginError = 'Invalid username or password';
    }
  }
}
