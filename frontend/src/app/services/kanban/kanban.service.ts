import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, Subject } from 'rxjs';
import { User } from 'src/app/models/auth.model';
import { handleError } from 'src/app/utils/exception.util';
import { environment } from 'src/environments/environment';
import {
  Board,
  Card,
  Column,
  CreateBoard,
  CreateCard,
  CreateColumn,
  UpdateBoardDto,
  UpdateCard,
} from '../../models/kanban.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class KanbanService {
  private columnArraySubject: BehaviorSubject<Column[]>;
  columns$: Observable<Column[]>;

  constructor(private http: HttpClient, private authService: AuthService) {
    this.columnArraySubject = new BehaviorSubject<Column[]>([]);
    this.columns$ = this.columnArraySubject.asObservable();
  }

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

  updateBoard(boardId: string, data: UpdateBoardDto): Observable<Board> {
    console.log('boardId', boardId);
    return this.http
      .put<Board>(`${environment.apiUrl}/boards/${boardId}`, data, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(
        catchError(
          handleError<Board>('addUserToBoard', {
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
            priority: {
              id: '',
              name: '',
              columnId: '',
            },
            columnId: '',
            creationDate: new Date(),
            updateDate: new Date(),
          })
        )
      );
  }

  getCardById(cardId: string): Observable<Card> {
    return this.http
      .get<Card>(`${environment.apiUrl}/boards/card/${cardId}`, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(
        catchError(
          handleError<Card>('getCardById', {
            id: '',
            name: '',
            order: 0,
            description: '',
            columnId: '',
            users: [],
            priority: {
              id: '',
              name: '',
              columnId: '',
            },
            creationDate: new Date(),
            updateDate: new Date(),
          })
        )
      );
  }

  sortCards(data: Card[]): Observable<Card[]> {
    return this.http
      .put<Card[]>(`${environment.apiUrl}/boards/card/sort`, data, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(catchError(handleError<Card[]>('sortCards', [])));
  }

  sortCardsAndUpdateColumn(data: Card[]): Observable<Card[]> {
    return this.http
      .put<Card[]>(`${environment.apiUrl}/boards/card/sortWithColumns`, data, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(catchError(handleError<Card[]>('sortCardsAndUpdateColumn', [])));
  }

  updateCard(id: string, data: UpdateCard): Observable<Card> {
    return this.http
      .put<Card>(`${environment.apiUrl}/boards/card/${id}`, data, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(
        catchError(
          handleError<Card>('updateCard', {
            id: '',
            name: '',
            order: 0,
            description: '',
            columnId: '',
            users: [],
            priority: {
              id: '',
              name: '',
              columnId: '',
            },
            creationDate: new Date(),
            updateDate: new Date(),
          })
        )
      );
  }

  setColumnArraySubject(data: Column[]): void {
    this.columnArraySubject.next(data);
  }

  addColumnToArraySubject(data: Column): void {
    const columns = this.columnArraySubject.getValue();
    columns.push(data);
    this.columnArraySubject.next(columns);
  }
}
