import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { apiURL } from '../../constants/api.constant';
import { handleError } from '../../utils/exception.util';

type LoginType = {
  email: string
  password: string
}

type UserType = {
  id: string
  email: string
  fullname: string
  image: string
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}
  
  setTokenToStorage(token: string): void {
    sessionStorage.setItem('token', token);
  }

  getTokenFromStorage(): string | null {
    const token = sessionStorage.getItem("token");
    if (token === null) return null;
    return token
  }

  isAuthenticated(): Observable<any> {
    return this.http.post<{token: string}>(`${apiURL}/auth/validate?token=${this.getTokenFromStorage()}`, null).pipe(
      catchError(handleError<{ token: string }>('isAuthenticated', { token: '' }))
    );
  }

  getUser(): Observable<UserType> {
    return this.http.get<UserType>(`${apiURL}/auth/get-user?token=${this.getTokenFromStorage()}`, {
      headers: {
        'Authorization': `Bearer ${this.getTokenFromStorage()}`
      }
    });
  }

  login(data: LoginType): Observable<{token: string}> {
    return this.http.post<{ token: string }>(`${apiURL}/auth/login`, data).pipe(
      catchError(handleError<{ token: string }>('login', { token: '' }))
    );
  }
}
