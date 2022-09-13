import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { handleError } from 'src/app/utils/exception.util';
import { environment } from 'src/environments/environment';
import {
  Board,
  Card,
  Column,
  CreateBoard,
  CreateCard,
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
      .get<Board[]>(`${environment.apiUrl}/boards/list?user=${userId}`, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(catchError(handleError<Board[]>('getBoards', [])));
  }

  createBoard(userId: string, data: CreateBoard): Observable<Board> {
    return this.http
      .post<Board>(
        `${environment.apiUrl}/boards`,
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
      .get<Board>(`${environment.apiUrl}/boards/${boardId}`, {
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
        `${environment.apiUrl}/boards/column`,
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
            cards: [],
            creationDate: new Date(),
            updateDate: new Date(),
          })
        )
      );
  }

  sortColumns(data: Column[]): Observable<Column[]> {
    return this.http
      .put<Column[]>(`${environment.apiUrl}/boards/column/sort`, data, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(catchError(handleError('sortColumns', [])));
  }

  sortCardsAndUpdateColumn(data: Card[]): Observable<Column[]> {
    return this.http
      .put<Column[]>(
        `${environment.apiUrl}/boards/cards/sort-and-update`,
        data,
        {
          headers: {
            Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
          },
        }
      )
      .pipe(catchError(handleError('sortColumnsAndCards', [])));
  }

  createCard(data: CreateCard): Observable<Card> {
    return this.http
      .post<Card>(
        `${environment.apiUrl}/boards/card`,
        {
          name: data.name,
          columnId: data.columnId,
        },
        {
          headers: {
            Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
          },
        }
      )
      .pipe(
        catchError(
          handleError<Card>('createCard', {
            id: '',
            name: '',
            description: '',
            order: 0,
            users: [],
            columnId: '',
            creationDate: new Date(),
            updateDate: new Date(),
          })
        )
      );
  }
}
