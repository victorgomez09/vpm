import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, ReplaySubject } from 'rxjs';
import { handleError } from 'src/app/utils/exception.util';
import { environment } from 'src/environments/environment';

import { Login, Register, User } from '../../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private userSubject: ReplaySubject<User>;
  public user$: Observable<User>;

  constructor(private http: HttpClient, private router: Router) {
    this.userSubject = new ReplaySubject<User>();
    this.user$ = this.userSubject.asObservable();
  }

  setTokenToStorage(token: string): void {
    sessionStorage.setItem('token', token);
  }

  getTokenFromStorage(): string | null {
    const token = sessionStorage.getItem('token');
    if (token === null) return null;
    return token;
  }

  isAuthenticated(): Observable<{ token: string }> {
    return this.http
      .post<{ token: string }>(
        `${
          environment.apiUrl
        }/auth/validate?token=${this.getTokenFromStorage()}`,
        null
      )
      .pipe(
        catchError(
          handleError<{ token: string }>('isAuthenticated', { token: '' })
        )
      );
  }

  getLoggedUser(): void {
    if (this.getTokenFromStorage()) {
      this.http
        .get<User>(
          `${
            environment.apiUrl
          }/auth/get-user?token=${this.getTokenFromStorage()}`,
          {
            headers: {
              Authorization: `Bearer ${this.getTokenFromStorage()}`,
            },
          }
        )
        .pipe(
          catchError(
            handleError<User>('getLoggedUser', {
              id: '',
              email: '',
              fullname: '',
              image: '',
            })
          )
        )
        .subscribe((data) => {
          this.userSubject.next(data);
        });
    } else {
      // this.userSubject.next();
    }
  }

  login(data: Login): Observable<{ token: string }> {
    return this.http
      .post<{ token: string }>(`${environment.apiUrl}/auth/login`, data)
      .pipe(catchError(handleError<{ token: string }>('login', { token: '' })));
  }

  register(data: Register): Observable<User> {
    return this.http.post<User>(`${environment.apiUrl}/auth/create`, data).pipe(
      catchError(
        handleError<User>('register', {
          id: '',
          email: '',
          fullname: '',
          image: '',
        })
      )
    );
  }

  logout(): void {
    sessionStorage.removeItem('token');
    this.userSubject.next({ id: '', email: '', fullname: '', image: '' });
    this.router.navigate(['/']);
  }
}
