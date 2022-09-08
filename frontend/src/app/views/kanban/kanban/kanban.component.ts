import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/core/models/auth.model';
import { Board } from 'src/app/core/models/kanban.model';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { KanbanService } from 'src/app/core/services/kanban/kanban.service';
import { getFirstWordOfString } from 'src/app/shared/utils/text.util';

@Component({
  selector: 'app-kanban',
  templateUrl: './kanban.component.html',
  styleUrls: ['./kanban.component.scss']
})
export class KanbanComponent implements OnInit {

  loading: boolean;
  boards: Board[];
  filteredBoards: Board[];
  user!: User;
  firstName!: string;
  boardForm: FormGroup;
  filterText: string;

  constructor(private fb: FormBuilder, private kanbanService: KanbanService, private authService: AuthService) {
    this.loading = true;
    this.boards = [];
    this.filteredBoards = [];
    this.boardForm = fb.group({
      name: ["", Validators.required],
      description: "",
      image: ""
    })
    this.filterText = "";
  }
  
  ngOnInit(): void {
    this.authService.user$.subscribe(data => {
      this.user = data;
      this.firstName = getFirstWordOfString(data.fullname);
      this.kanbanService.getBoards(data.id).subscribe(data => {
        this.boards = data
        this.filteredBoards = data;
        this.loading = false;
      });
    })
  }

  createBoard(): void {
    const data = {
      name: this.boardForm.value.name,
      description: this.boardForm.value.description,
      image: this.boardForm.value.image
    }
    this.kanbanService.createBoard(this.user?.id, data).subscribe(data => {
      this.boards.push(data);
    })
  }

  search() {
    this.filteredBoards = this.filterText === "" ? this.boards : this.boards.filter((element) => {
      return element.name.toLowerCase().includes(this.filterText.toLowerCase());
    });
  }

  get f() {
    return this.boardForm.controls;
  }
}
