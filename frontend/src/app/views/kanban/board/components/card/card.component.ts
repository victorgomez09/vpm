import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Card } from 'src/app/models/kanban.model';
import { KanbanService } from 'src/app/services/kanban/kanban.service';
import { getInitials } from 'src/app/utils/text.util';

@Component({
  standalone: true,
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss'],
  imports: [CommonModule, FormsModule, RouterModule],
})
export class CardComponent implements OnInit {
  @Input()
  card!: Card;
  editDescription: boolean;
  description!: string;

  constructor(private kanbanService: KanbanService) {
    this.editDescription = false;
  }

  ngOnInit(): void {
    this.description = this.card.description;
    console.log('card', this.card);
  }

  getInitials(text: string): string {
    return getInitials(text);
  }
}
