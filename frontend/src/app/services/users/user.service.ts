import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable } from 'rxjs';
import { User } from 'src/app/models/auth.model';
import { handleError } from 'src/app/utils/exception.util';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllUsers(): Observable<User[]> {
    return this.http
      .get<User[]>(`${environment.apiUrl}/users/list`, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(catchError(handleError<User[]>('getAllUsers', [])));
  }
}
