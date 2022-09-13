import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-kanban-template',
  templateUrl: './kanban-template.component.html',
  styleUrls: ['./kanban-template.component.scss'],
  imports: [RouterModule],
})
export class KanbanTemplateComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
