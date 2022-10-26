import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable } from 'rxjs';
import { handleError } from 'src/app/utils/exception.util';
import { environment } from 'src/environments/environment';
import {
  Board,
  Card,
  Column,
  CreateBoard,
  CreateCard,
  CreateColumn,
  CreateProject,
  Project,
  UpdateBoardDto,
  UpdateCard,
} from '../../models/issue-tracker.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class KanbanService {
  private columnArraySubject: BehaviorSubject<Column[]>;
  public columns$: Observable<Column[]>;
  private subPath: string;
  private httpParams: HttpParams;

  constructor(private http: HttpClient, private authService: AuthService) {
    this.columnArraySubject = new BehaviorSubject<Column[]>([]);
    this.columns$ = this.columnArraySubject.asObservable();
    this.subPath = '/api/issue-tracker';
    this.httpParams = new HttpParams();
  }

  getBoards(userId: string): Observable<Board[]> {
    return this.http
      .get<Board[]>(`${environment.apiUrl}${this.subPath}/boards`, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
        params: this.httpParams.set('user', userId),
      })
      .pipe(catchError(handleError<Board[]>('getBoards', [])));
  }

  getBoardById(boardId: string): Observable<Board> {
    return this.http
      .get<Board>(`${environment.apiUrl}/api/issue-tracker/boards/${boardId}`, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(
        catchError(
          handleError<Board>('getBoardById', {
            id: '',
            name: '',
            code: '',
            description: '',
            color: '',
            image: '',
            columns: [],
            users: [],
            creationDate: new Date(),
            updateDate: new Date(),
          })
        )
      );
  }

  createBoard(data: CreateBoard): Observable<Board> {
    return this.http
      .post<Board>(`${environment.apiUrl}/api/issue-tracker/boards`, data, {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      })
      .pipe(
        catchError(
          handleError<Board>('createBoard', {
            id: '',
            name: '',
            code: '',
            description: '',
            color: '',
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
      .put<Board>(
        `${environment.apiUrl}/api/issue-tracker/boards/${boardId}`,
        data,
        {
          headers: {
            Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
          },
        }
      )
      .pipe(
        catchError(
          handleError<Board>('addUserToBoard', {
            id: '',
            name: '',
            code: '',
            description: '',
            color: '',
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
        `${environment.apiUrl}/api/issue-tracker/columns`,
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
      .put<Column[]>(
        `${environment.apiUrl}/api/issue-tracker/columns/sort`,
        data,
        {
          headers: {
            Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
          },
        }
      )
      .pipe(catchError(handleError('sortColumns', [])));
  }

  createCard(data: CreateCard): Observable<Card> {
    return this.http
      .post<Card>(
        `${environment.apiUrl}/api/issue-tracker/cards`,
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
      .get<Card>(
        `${environment.apiUrl}/api/issue-tracker/cards/${cardId}`,
        {
          headers: {
            Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
          },
        }
      )
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
      .put<Card[]>(
        `${environment.apiUrl}/api/issue-tracker/cards/sort`,
        data,
        {
          headers: {
            Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
          },
        }
      )
      .pipe(catchError(handleError<Card[]>('sortCards', [])));
  }

  sortCardsAndUpdateColumn(data: Card[]): Observable<Card[]> {
    return this.http
      .put<Card[]>(
        `${environment.apiUrl}/api/issue-tracker/cards/sortWithColumns`,
        data,
        {
          headers: {
            Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
          },
        }
      )
      .pipe(catchError(handleError<Card[]>('sortCardsAndUpdateColumn', [])));
  }

  updateCard(id: string, data: UpdateCard): Observable<Card> {
    return this.http
      .put<Card>(
        `${environment.apiUrl}/api/issue-tracker/cards/${id}`,
        data,
        {
          headers: {
            Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
          },
        }
      )
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
