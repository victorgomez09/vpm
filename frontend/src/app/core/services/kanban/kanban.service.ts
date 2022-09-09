import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, iif, map, Observable, switchMap } from 'rxjs';
import { handleError } from 'src/app/shared/utils/exception.util';
import { apiURL } from '../../constants/api.constant';
import { User } from '../../models/auth.model';
import {
  Board,
  Column,
  CreateBoard,
  CreateColumn,
} from '../../models/kanban.model';
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

  createBoard(userId: string, data: CreateBoard): Observable<Board> {
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

  getBoardById(boardId: string): Observable<Board> {
    return this.http
      .get<Board>(`${apiURL}/boards/${boardId}`, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(
        catchError(
          handleError<Board>('getBoardById', {
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

  createColumn(data: CreateColumn): Observable<Column> {
    return this.http
      .post<Column>(
        `${apiURL}/boards/column`,
        {
          name: data.name,
          boardId: data.boardId,
        },
        {
          headers: {
            Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
          },
        }
      )
      .pipe(
        catchError(
          handleError<Column>('createColumn', {
            id: '',
            name: '',
            order: 0,
            creationDate: new Date(),
            updateDate: new Date(),
          })
        )
      );
  }

  sortColumns(data: Column[]): Observable<Column[]> {
    return this.http
      .put<Column[]>(`${apiURL}/boards/column/sort`, data, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(catchError(handleError('sortColumns', [])));
  }
}
