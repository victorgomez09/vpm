import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Board, Column, CreateColumn } from 'src/app/core/models/kanban.model';
import { KanbanService } from 'src/app/core/services/kanban/kanban.service';
import { sortColumns } from 'src/app/shared/utils/kanban.util';

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
        console.log('data', data);
      });
    });
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.columns, event.previousIndex, event.currentIndex);
    this.columns = sortColumns(this.columns);
    console.log('columns', this.columns);
    this.kanbanService.sortColumns(this.columns).subscribe((data) => {
      console.log('data from sort', data);
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
