import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/auth.model';
import { Board, CreateBoard } from 'src/app/models/issue-tracker.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { KanbanService } from 'src/app/services/kanban/kanban.service';
import { getFirstWordOfString, getInitials } from 'src/app/utils/text.util';

@Component({
  selector: 'app-kanban',
  templateUrl: './kanban.component.html',
  styleUrls: ['./kanban.component.scss'],
})
export class KanbanComponent implements OnInit {
  loading: boolean;
  boards: Board[];
  filteredBoards: Board[];
  user!: User;
  firstName!: string;
  boardForm: FormGroup;
  filterText: string;
  boardBackgroundColor: string;
  boardGeneratedCode: string;
  createdBoard: boolean;

  constructor(
    private fb: FormBuilder,
    private kanbanService: KanbanService,
    private authService: AuthService
  ) {
    this.loading = true;
    this.boards = [];
    this.filteredBoards = [];
    this.boardForm = fb.group({
      code: ['', Validators.required],
      name: ['', Validators.required],
      description: '',
    });
    this.filterText = '';
    this.boardBackgroundColor = 'light';
    this.boardGeneratedCode = '';
    this.createdBoard = false;
  }

  ngOnInit(): void {
    this.authService.user$.subscribe((data) => {
      this.user = data;
      this.firstName = getFirstWordOfString(data.fullname);
      this.kanbanService.getBoards(data.id).subscribe((data) => {
        this.boards = data;
        this.filteredBoards = data;
        this.loading = false;
      });
    });
  }

  createBoard(): void {
    const data: CreateBoard = {
      name: this.boardForm.value.name,
      code: this.boardForm.value.code,
      description: this.boardForm.value.description,
      color: this.boardBackgroundColor,
      users: [this.user.id],
    };

    this.kanbanService.createBoard(data).subscribe((data) => {
      this.boards.push(data);
      this.createdBoard = true;
    });
  }

  handleChangeColor(newColor: string): void {
    this.boardBackgroundColor = newColor;
  }

  resetForm(): void {
    this.createdBoard = false;
    this.boardForm.reset();
  }

  search(): void {
    this.filteredBoards =
      this.filterText === ''
        ? this.boards
        : this.boards.filter((element) => {
            return element.name
              .toLowerCase()
              .includes(this.filterText.toLowerCase());
          });
  }

  generateCode(): void {
    this.boardForm.controls['code'].setValue(
      getInitials(this.boardForm.value.name)
    );
  }

  get f() {
    return this.boardForm.controls;
  }
}
