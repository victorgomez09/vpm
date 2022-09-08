import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/core/models/auth.model';
import { Board } from 'src/app/core/models/kanban.model';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { KanbanService } from 'src/app/core/services/kanban/kanban.service';

@Component({
  selector: 'app-kanban',
  templateUrl: './kanban.component.html',
  styleUrls: ['./kanban.component.scss']
})
export class KanbanComponent implements OnInit {

  boards: Board[];
  user!: User;
  boardForm: FormGroup

  constructor(private fb: FormBuilder, private kanbanService: KanbanService, private authService: AuthService) {
    this.boards = [];
    this.boardForm = fb.group({
      name: ["", Validators.required],
      description: "",
      image: ""
    })
  }
  
  ngOnInit(): void {
    this.authService.user$.subscribe(data => {
      this.user = data;
      this.kanbanService.getBoards(data.id).subscribe(data => this.boards = data);
    })
  }

  createBoard(): void {
    console.log("form values", this.boardForm.value)
    console.log("user", this.user);
    const data = {
      name: this.boardForm.value.name,
      description: this.boardForm.value.description,
      image: this.boardForm.value.image
    }
    this.kanbanService.createBoard(this.user?.id, data).subscribe(data => console.log('data', data))
  }

  get f() {
    return this.boardForm.controls;
  }
}
