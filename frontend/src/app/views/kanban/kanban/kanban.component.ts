import { Component, OnInit } from '@angular/core';
import { Board } from 'src/app/core/models/kanban.model';
import { KanbanService } from 'src/app/core/services/kanban/kanban.service';

@Component({
  selector: 'app-kanban',
  templateUrl: './kanban.component.html',
  styleUrls: ['./kanban.component.scss']
})
export class KanbanComponent implements OnInit {

  boards?: Board[];

  constructor(private kanbanService: KanbanService) { }

  ngOnInit(): void {
    this.kanbanService.getBoards().subscribe(data => this.boards = data);
  }
  
}
