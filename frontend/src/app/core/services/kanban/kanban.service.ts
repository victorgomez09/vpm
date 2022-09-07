import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { handleError } from 'src/app/shared/utils/exception.util';
import { apiURL } from '../../constants/api.constant';
import { User } from '../../models/auth.model';
import { Board } from '../../models/kanban.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class KanbanService {

  user?: User;

  constructor(private http: HttpClient, private authService: AuthService) {
    this.authService.user$.subscribe(data => {
      if (data) this.user = data
    })
    console.log('kanban service constructor', this.user);
  }

  getBoards(): Observable<Board[]> {
    console.log('this.user', this.user)
    return this.http.get<Board[]>(`${apiURL}/boards?user=${this.user?.id}`, {
      headers: {
        'Authorization': `Bearer ${this.authService.getTokenFromStorage()}`
      }
    }).pipe(
      catchError(handleError<Board[]>('getBoards', []))
    );
  }
}
