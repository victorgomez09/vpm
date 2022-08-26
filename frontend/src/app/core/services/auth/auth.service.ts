import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, Subject } from 'rxjs';
import { handleError } from '../../../shared/utils/exception.util';
import { apiURL } from '../../constants/api.constant';
import { ILogin, IRegister, IUser } from '../../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private userSubject: BehaviorSubject<IUser | null>;
  public user$: Observable<IUser | null>;

  constructor(private http: HttpClient) {
    // this.userSubject = new BehaviorSubject<IUser>(
    //   JSON.parse(localStorage.getItem('currentUser')!)
    // );
    console.log('auth service constructor')
    this.userSubject = new BehaviorSubject<IUser | null>(null);
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
    return token
  }

  isAuthenticated(): Observable<{token: string}> {
    //TODO: Temporaly solution, need to change in backend 
    // this.getUser();
    return this.http.post<{token: string}>(`${apiURL}/auth/validate?token=${this.getTokenFromStorage()}`, null).pipe(
      catchError(handleError<{ token: string }>('isAuthenticated', { token: '' }))
    );
  }

  getLoggedUser(): void {
    if (this.getTokenFromStorage()) {
      console.log('fetching user');
      this.http.get<IUser>(`${apiURL}/auth/get-user?token=${this.getTokenFromStorage()}`, {
        headers: {
          'Authorization': `Bearer ${this.getTokenFromStorage()}`
        }
      }).subscribe(data => {
        this.userSubject.next(data)
        console.log('next called');
      });
    } else {
      console.log('setting null subject');
      this.userSubject.next(null);
    }
  }

  login(data: ILogin): Observable<{token: string}> {
    return this.http.post<{ token: string }>(`${apiURL}/auth/login`, data).pipe(
      catchError(handleError<{ token: string }>('login', { token: '' }))
    );
  }

  register(data: IRegister): Observable<IUser> {
    return this.http.post<IUser>(`${apiURL}/auth/create`, data).pipe(
      catchError(handleError<IUser>('register', {id: '', email: '', fullname: '', image: ''}))
    );
  }
}
