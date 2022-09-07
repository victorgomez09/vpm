import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-column',
  templateUrl: './column.component.html',
  styleUrls: ['./column.component.scss']
})
export class ColumnComponent implements OnInit {
  addNewTask: boolean;

  constructor() {
    this.addNewTask = false;
  }

  ngOnInit(): void {
  }

  taskDrop(event: CdkDragDrop<string[]>) {
    // moveItemInArray(this.board.tasks, event.previousIndex, event.currentIndex);
    // this.boardService.updateTasks(this.board.id, this.board.tasks);
  }
}
