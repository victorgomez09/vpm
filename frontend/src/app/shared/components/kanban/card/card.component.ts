import { Component, Input, OnInit } from '@angular/core';
import { Card } from 'src/app/core/models/kanban.model';
import { KanbanService } from 'src/app/core/services/kanban/kanban.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
export class CardComponent implements OnInit {

  @Input()
  card!: Card;

  constructor(private kanbanService: KanbanService) { }

  ngOnInit(): void {
  }

}
