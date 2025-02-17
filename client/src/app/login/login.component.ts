import { Component } from '@angular/core';
import { LoginFormComponent } from '../login-form/login-form.component';
import { LoginRightComponent } from '../login-right/login-right.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [LoginFormComponent, LoginRightComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

}
