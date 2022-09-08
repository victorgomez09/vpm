import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, ReplaySubject, Subject } from 'rxjs';
import { handleError } from '../../../shared/utils/exception.util';
import { apiURL } from '../../constants/api.constant';
import { Login, Register, User } from '../../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private userSubject: ReplaySubject<User>;
  public user$: Observable<User>;

  constructor(private http: HttpClient) {
    // this.userSubject = new BehaviorSubject<IUser>(
    //   JSON.parse(localStorage.getItem('currentUser')!)
    // );
    console.log('auth service constructor')
    this.userSubject = new ReplaySubject<User>();
    this.user$ = this.userSubject.asObservable();
    // Fetch user from database
    // this.getUser();
  }
  
  setTokenToStorage(token: string): void {
    sessionStorage.setItem('token', token);
  }

  getTokenFromStorage(): string | null {
    const token = sessionStorage.getItem("token");
    if (token === null) return null;
    return token;
  }

  isAuthenticated(): Observable<{token: string}> {
    return this.http.post<{token: string}>(`${apiURL}/auth/validate?token=${this.getTokenFromStorage()}`, null).pipe(
      catchError(handleError<{ token: string }>('isAuthenticated', { token: '' }))
    );
  }

  getLoggedUser(): void {
    if (this.getTokenFromStorage()) {
      console.log('getting user from auth service');
      this.http.get<User>(`${apiURL}/auth/get-user?token=${this.getTokenFromStorage()}`, {
        headers: {
          'Authorization': `Bearer ${this.getTokenFromStorage()}`
        }
      })
      .pipe(catchError(handleError<User>('getLoggedUser', { id: '', email: '', fullname: '', image: '' })))
      .subscribe(data => {
        console.log('getLoggedUser', data)
        this.userSubject.next(data)
      });
    } else {
      // this.userSubject.next();
    }
  }

  login(data: Login): Observable<{token: string}> {
    return this.http.post<{ token: string }>(`${apiURL}/auth/login`, data).pipe(
      catchError(handleError<{ token: string }>('login', { token: '' }))
    );
  }

  register(data: Register): Observable<User> {
    return this.http.post<User>(`${apiURL}/auth/create`, data).pipe(
      catchError(handleError<User>('register', {id: '', email: '', fullname: '', image: ''}))
    );
  }
}
