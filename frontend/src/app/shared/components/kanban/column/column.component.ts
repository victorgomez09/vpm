import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
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
      name: ["", Validators.required]
    })
  }

  ngOnInit(): void {}

  taskDrop(event: CdkDragDrop<string[]>) {
    // moveItemInArray(this.board.tasks, event.previousIndex, event.currentIndex);
    // this.boardService.updateTasks(this.board.id, this.board.tasks);
  }

  submit(event: Event): void {
    event.preventDefault();
  }

  get f() {
    return this.cardForm.controls;
  }
}
