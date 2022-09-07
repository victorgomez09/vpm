import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Column } from 'src/app/core/models/kanban.model';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {

  columns: Column[];
  addNewColumn: boolean;
  columnForm!: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.columns = [];
    this.addNewColumn = false;
  }

  ngOnInit(): void {
    this.initForm()
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.columns, event.previousIndex, event.currentIndex);
    console.log('columns', this.columns);
    // this.boardService.sortBoards(this.boards);
  }

  initForm() {
    this.columnForm = this.formBuilder.group({
      name: ['', Validators.required],
    });
  }

  submit(event: Event) {
    event.preventDefault()
    this.addNewColumn = false;
    this.columnForm.value.name.replace(/[\r\n]+/,"");
  }

  get f() {
    return this.columnForm.controls;
  }
}
