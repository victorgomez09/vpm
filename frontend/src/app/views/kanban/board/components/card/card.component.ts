import { Component, Input, OnInit } from '@angular/core';
import { Card } from 'src/app/models/kanban.model';
import { KanbanService } from 'src/app/services/kanban/kanban.service';

@Component({
  standalone: true,
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss'],
})
export class CardComponent implements OnInit {
  @Input()
  card!: Card;

  constructor(private kanbanService: KanbanService) {}

  ngOnInit(): void {}
}
