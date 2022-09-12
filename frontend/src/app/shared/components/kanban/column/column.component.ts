import {
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Card, Column } from 'src/app/core/models/kanban.model';

@Component({
  selector: 'app-column',
  templateUrl: './column.component.html',
  styleUrls: ['./column.component.scss'],
})
export class ColumnComponent implements OnInit {
  @Input()
  column!: Column;
  cards: Card[];
  addNewTask: boolean;
  cardForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.addNewTask = false;
    this.cards = [];
    this.cardForm = this.fb.group({
      name: ['', Validators.required],
    });
  }

  ngOnInit(): void {}

  taskDrop(event: CdkDragDrop<Card[]>) {
    if (event.container.id !== event.previousContainer.id) {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    } else {
      moveItemInArray(this.cards, event.previousIndex, event.currentIndex);
    }
    // this.boardService.updateTasks(this.board.id, this.board.tasks);
  }

  submit(event: Event): void {
    event.preventDefault();
    this.addNewTask = false;

    const payload: Card = {
      name: (event.target as HTMLTextAreaElement).value,
    };
    this.cards.push(payload);
    console.log('card added to cards', payload);
    console.log('cards with push', this.cards);
  }

  get f() {
    return this.cardForm.controls;
  }
}
