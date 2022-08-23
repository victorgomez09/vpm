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
  private userSubject: Subject<IUser>;
  public user: Observable<IUser>;

  constructor(private http: HttpClient) {
    // this.userSubject = new BehaviorSubject<IUser>(
    //   JSON.parse(localStorage.getItem('currentUser')!)
    // );
    this.userSubject = new Subject<IUser>();
    this.user = this.userSubject.asObservable();
  }
  
  setTokenToStorage(token: string): void {
    sessionStorage.setItem('token', token);
  }

  getTokenFromStorage(): string | null {
    const token = sessionStorage.getItem("token");
    if (token === null) return null;
    return token
  }

  isAuthenticated(): Observable<any> {
    //TODO: Temporaly solution, need to change in backend 
    // this.getUser();
    return this.http.post<{token: string}>(`${apiURL}/auth/validate?token=${this.getTokenFromStorage()}`, null).pipe(
      catchError(handleError<{ token: string }>('isAuthenticated', { token: '' }))
    );
  }

  getUser(): void {
    this.http.get<IUser>(`${apiURL}/auth/get-user?token=${this.getTokenFromStorage()}`, {
      headers: {
        'Authorization': `Bearer ${this.getTokenFromStorage()}`
      }
    }).subscribe(data => this.userSubject.next(data));
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

  // get userValue(): IUser {
  //   return this.userSubject.asObservable();
  // }
}
