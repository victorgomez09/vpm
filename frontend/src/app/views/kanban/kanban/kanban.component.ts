import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/auth.model';
import { Board } from 'src/app/models/kanban.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { KanbanService } from 'src/app/services/kanban/kanban.service';
import { getFirstWordOfString } from 'src/app/utils/text.util';

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
  createdBoard: boolean;
  displayType: 'GRID' | 'TABLE';
  hideSearchInput: boolean;
  newBoardBackgroundColor: string;

  constructor(
    private fb: FormBuilder,
    private kanbanService: KanbanService,
    private authService: AuthService
  ) {
    this.loading = true;
    this.boards = [];
    this.filteredBoards = [];
    this.boardForm = fb.group({
      name: ['', Validators.required],
      description: '',
    });
    this.filterText = '';
    this.createdBoard = false;
    this.displayType = 'GRID';
    this.hideSearchInput = false;
    this.newBoardBackgroundColor = 'bg-light';
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
    const data = {
      name: this.boardForm.value.name,
      description: this.boardForm.value.description,
      image: this.newBoardBackgroundColor,
    };
    this.kanbanService.createBoard(this.user?.id, data).subscribe((data) => {
      this.boards.push(data);
      this.createdBoard = true;
    });
  }

  handleChangeBoardColor(newColor: string): void {
    this.newBoardBackgroundColor = newColor;
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

  handleDisplayType(event: Event): void {
    this.displayType =
      (event.target as HTMLInputElement).value === 'GRID' ? 'GRID' : 'TABLE';
    console.log('displayType', this.displayType);
  }

  handleHideSearchInput(event: Event): void {
    this.hideSearchInput = (event.target as HTMLInputElement).checked;
  }

  get f() {
    return this.boardForm.controls;
  }
}
