import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, iif, map, Observable, switchMap } from 'rxjs';
import { handleError } from 'src/app/shared/utils/exception.util';
import { apiURL } from '../../constants/api.constant';
import { User } from '../../models/auth.model';
import { Board } from '../../models/kanban.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class KanbanService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getBoards(userId: string): Observable<Board[]> {
    return this.http
      .get<Board[]>(`${apiURL}/boards/list?user=${userId}`, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(catchError(handleError<Board[]>('getBoards', [])));
  }

  createBoard(userId: string, data: {
    name: string,
    description: string,
    image: string
  }): Observable<Board> {
    return this.http
      .post<Board>(
        `${apiURL}/boards`,
        {
          name: data.name,
          description: data.description,
          image: data.image,
          users: [userId],
        },
        {
          headers: {
            Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
          },
        }
      )
      .pipe(
        catchError(
          handleError<Board>('createBoard', {
            id: '',
            name: '',
            description: '',
            image: '',
            columns: [],
            users: [],
            creationDate: new Date(),
            updateDate: new Date(),
          })
        )
      );
  }
}
