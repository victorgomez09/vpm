import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/auth.model';
import {
  Board,
  Column,
  CreateColumn,
  UpdateBoardDto,
} from 'src/app/models/kanban.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { KanbanService } from 'src/app/services/kanban/kanban.service';
import { UserService } from 'src/app/services/users/user.service';
import { sortColumns } from 'src/app/utils/kanban.util';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
})
export class BoardComponent implements OnInit {
  board!: Board;
  columns$: Observable<Column[]>;
  addNewColumn: boolean;
  columnForm: FormGroup;
  users: User[];
  filteredUsers: User[];
  filteredText: string;
  permissionBoard: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private authService: AuthService,
    private kanbanService: KanbanService,
    private userService: UserService
  ) {
    this.columns$ = this.kanbanService.columns$;
    this.addNewColumn = false;
    this.columnForm = this.formBuilder.group({
      name: ['', Validators.required],
    });
    this.users = [];
    this.filteredUsers = [];
    this.filteredText = '';
    this.permissionBoard = false;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id')!;
      this.kanbanService.getBoardById(id).subscribe((data) => {
        this.board = data;
        this.kanbanService.setColumnArraySubject(data.columns);
        this.authService.user$.subscribe((user) => {
          if (!data.users.map((element) => element.id).includes(user.id))
            this.permissionBoard = false;
          else this.permissionBoard = true;
          return;
        });
      });
    });
    this.userService.getAllUsers().subscribe((data) => {
      this.users = data;
      this.filteredUsers = data;
    });
  }

  drop(event: CdkDragDrop<Column[]>) {
    this.columns$.subscribe((data) => {
      moveItemInArray(data, event.previousIndex, event.currentIndex);
      sortColumns(data);
      this.kanbanService.sortColumns(data).subscribe({
        error: () => console.log('something goes wrong'),
      });
    });
  }

  submit(event: Event) {
    event.preventDefault();
    this.addNewColumn = false;

    const data: CreateColumn = {
      name: this.columnForm.value.name.replace(/[\r\n]+/, ''),
      boardId: this.board!.id,
    };
    this.kanbanService.createColumn(data).subscribe((data) => {
      this.kanbanService.addColumnToArraySubject(data);
    });
  }

  search(): void {
    this.filteredUsers =
      this.filteredText === ''
        ? this.users
        : this.users.filter((element) => {
            return element.fullname
              .toLowerCase()
              .includes(this.filteredText.toLowerCase());
          });
  }

  addUserToBoard(user: User): void {
    this.authService.user$.subscribe((data) => {
      console.log('auth', data);
    });
    const usersArrayWithIds = this.board.users.map((element) => element.id);
    !usersArrayWithIds.includes(user.id)
      ? usersArrayWithIds.push(user.id)
      : usersArrayWithIds.splice(usersArrayWithIds.indexOf(user.id), 1);
    const payload: UpdateBoardDto = {
      name: this.board.name,
      description: this.board.description,
      image: this.board.image,
      users: usersArrayWithIds,
    };
    this.kanbanService.updateBoard(this.board.id, payload).subscribe((data) => {
      this.board.users = data.users;
    });
  }

  get f() {
    return this.columnForm.controls;
  }
}
