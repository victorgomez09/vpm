import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Board, Column, CreateColumn } from 'src/app/models/kanban.model';
import { KanbanService } from 'src/app/services/kanban/kanban.service';
import { sortColumns } from 'src/app/utils/kanban.util';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
})
export class BoardComponent implements OnInit {
  board?: Board;
  columns: Column[];
  addNewColumn: boolean;
  columnForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private kanbanService: KanbanService
  ) {
    this.columns = [];
    this.addNewColumn = false;
    this.columnForm = this.formBuilder.group({
      name: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id')!;
      this.kanbanService.getBoardById(id).subscribe((data) => {
        this.board = data;
        this.columns = data.columns;
      });
    });
  }

  drop(event: CdkDragDrop<Column[]>) {
    moveItemInArray(this.columns, event.previousIndex, event.currentIndex);
    console.log('columns', this.columns);
    sortColumns(this.columns);
    this.kanbanService.sortColumns(this.columns).subscribe({
      error: () => console.log('something goes wrong'),
    });
  }

  submit(event: Event) {
    event.preventDefault();
    this.addNewColumn = false;

    const data: CreateColumn = {
      name: this.columnForm.value.name.replace(/[\r\n]+/, ''),
      boardId: this.board!.id,
    };
    this.kanbanService.createColumn(data).subscribe((data) => {
      this.columns.push(data);
    });
  }

  get f() {
    return this.columnForm.controls;
  }
}
