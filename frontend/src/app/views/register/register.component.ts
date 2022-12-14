import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Register } from '../../models/auth.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  registeredError?: boolean;
  registeredErrorCode?: number;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.registerForm = this.formBuilder.group(
      {
        email: new FormControl('', [Validators.email, Validators.required]),
        fullname: new FormControl('', Validators.required),
        password: new FormControl('', [
          Validators.required,
          Validators.minLength(6),
        ]),
        confirmPassword: new FormControl('', [
          Validators.required,
          Validators.minLength(6),
        ]),
      },
      { validators: this.confirmed('password', 'confirmPassword') }
    );
  }

  confirmed = (controlName: string, matchingControlName: string) => {
    return (control: AbstractControl): ValidationErrors | null => {
      const input = control.get(controlName);
      const matchingInput = control.get(matchingControlName);

      if (input === null || matchingInput === null) {
        return null;
      }

      if (
        matchingInput?.errors &&
        (matchingInput.errors['confirmedValidator'] ||
          matchingInput.errors['required'])
      ) {
        return null;
      }

      if (input.value !== matchingInput.value) {
        matchingInput.setErrors({ confirmedValidator: true });
        return { confirmedValidator: true };
      } else {
        matchingInput.setErrors(null);
        return null;
      }
    };
  };

  onSubmit(): void {
    const userData: Register = {
      email: this.registerForm.value['email'],
      fullname: this.registerForm.value['fullname'],
      password: this.registerForm.value['password'],
      image: '',
    };

    this.authService.register(userData).subscribe({
      complete: () => {
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.registeredError = true;
        this.registeredErrorCode = error.status;
      },
    });
  }

  get f() {
    return this.registerForm.controls;
  }
}
