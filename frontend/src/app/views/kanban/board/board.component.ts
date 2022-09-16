import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/auth.model';
import { Board, Column, CreateColumn } from 'src/app/models/kanban.model';
import { KanbanService } from 'src/app/services/kanban/kanban.service';
import { UserService } from 'src/app/services/users/user.service';
import { sortColumns } from 'src/app/utils/kanban.util';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
})
export class BoardComponent implements OnInit {
  board?: Board;
  columns$: Observable<Column[]>;
  addNewColumn: boolean;
  columnForm: FormGroup;
  users: User[];
  filteredUsers: User[];
  filteredText: string;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
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
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id')!;
      this.kanbanService.getBoardById(id).subscribe((data) => {
        this.board = data;
        this.kanbanService.setColumnArraySubject(data.columns);
      });
    });
    this.userService.getAllUsers().subscribe((data) => {
      this.users = data;
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
        ? []
        : this.users.filter((element) => {
            return element.fullname
              .toLowerCase()
              .includes(this.filteredText.toLowerCase());
          });
  }

  get f() {
    return this.columnForm.controls;
  }
}
