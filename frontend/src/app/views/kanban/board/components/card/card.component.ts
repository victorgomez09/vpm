import { CommonModule } from '@angular/common';
import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
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
export class CardComponent implements OnInit, OnChanges {
  @Input()
  card!: Card;

  constructor(private kanbanService: KanbanService) {}

  ngOnChanges(changes: SimpleChanges): void {
    console.log('changes', changes);
    // throw new Error('Method not implemented.');
  }

  ngOnInit(): void {
    // this.kanbanService.card$.subscribe((data) => {
    //   console.log('cardData', data);
    //   this.card = data;
    // });
  }

  getInitials(text: string): string {
    return getInitials(text);
  }
}
