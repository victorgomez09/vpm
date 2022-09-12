import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loginError?: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.buildFrom();
  }

  buildFrom(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  submit() {
    const user = this.loginForm.value;
    this.authService.login(user).subscribe({
      next: (data) => this.handleLogin(data.token),
      error: () => this.loginError = true
    });
  }

  handleLogin(token: string) {
    this.router.navigate(['/dashboard']);
    this.authService.setTokenToStorage(token)
  }

  get f() {
    return this.loginForm.controls;
  }
}
