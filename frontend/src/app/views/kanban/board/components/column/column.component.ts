import {
  CdkDragDrop,
  DragDropModule,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Card, Column, CreateCard } from 'src/app/models/kanban.model';
import { KanbanService } from 'src/app/services/kanban/kanban.service';
import { sortCards, updateColumForCard } from 'src/app/utils/kanban.util';
import { CardComponent } from '../card';

@Component({
  standalone: true,
  selector: 'app-column',
  templateUrl: './column.component.html',
  styleUrls: ['./column.component.scss'],
  imports: [CommonModule, DragDropModule, ReactiveFormsModule, CardComponent],
})
export class ColumnComponent implements OnInit {
  @Input()
  column!: Column;
  cards: Card[];
  addNewTask: boolean;
  cardForm: FormGroup;

  constructor(private fb: FormBuilder, private kanbanService: KanbanService) {
    this.addNewTask = false;
    this.cards = [];
    this.cardForm = this.fb.group({
      name: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.cards = this.column.cards;
  }

  taskDrop(event: CdkDragDrop<Card[]>) {
    if (event.container.id !== event.previousContainer.id) {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
      updateColumForCard(event.container.data, event.item.data);
      sortCards(event.previousContainer.data);
      sortCards(event.container.data);
      const payload = [
        ...event.previousContainer.data,
        ...event.container.data,
      ];
      console.log('payload', payload);
      this.kanbanService
        .sortCardsAndUpdateColumn(payload)
        .subscribe((data) => console.log('data', data));
    } else {
      moveItemInArray(this.cards, event.previousIndex, event.currentIndex);
      console.log('this.cards', this.cards);
    }
    // this.kanbanService.sortCards()
    // this.boardService.updateTasks(this.board.id, this.board.tasks);
  }

  submit(event: Event): void {
    event.preventDefault();
    this.addNewTask = false;

    const payload: CreateCard = {
      name: (event.target as HTMLTextAreaElement).value,
      columnId: this.column.id,
    };
    this.kanbanService
      .createCard(payload)
      .subscribe((data) => this.cards.push(data));
  }

  get f() {
    return this.cardForm.controls;
  }
}
