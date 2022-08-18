import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  AbstractControlOptions,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { map } from 'rxjs';
import { apiURL } from 'src/app/shared/constants/api.constant';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private http: HttpClient) {}

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
        !matchingInput.errors['confirmedValidator']
      ) {
        return null;
      }

      if (input.value !== matchingInput.value) {
        matchingInput.setErrors({ confirmedValidator: true });
        console.log('matching', matchingInput.errors);
        return { confirmedValidator: true };
      } else {
        matchingInput.setErrors(null);
        return null;
      }
    };
  };

  onSubmit(): void {
    const userData = {
      email: this.registerForm.value['email'],
      fullname: this.registerForm.value['fullname'],
      password: this.registerForm.value['password'],
    };
    console.log('sending form to backend', userData);

    this.http
      .post(`${apiURL}/auth/create`, userData)
      .subscribe((data) => console.log('data', data));
  }

  get f() {
    return this.registerForm.controls;
  }
}
