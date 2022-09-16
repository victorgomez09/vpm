import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Priority } from 'src/app/models/kanban.model';

@Component({
  standalone: true,
  selector: 'app-priority-dropdown',
  templateUrl: './priority-dropdown.component.html',
  styleUrls: ['./priority-dropdown.component.scss'],
  imports: [CommonModule],
})
export class PriorityDropdownComponent implements OnInit {
  @Input()
  priority?: Priority;
  @Output()
  newPriorityEvent: EventEmitter<string>;

  constructor() {
    this.newPriorityEvent = new EventEmitter();
  }

  ngOnInit(): void {}

  changePriority(priorityName: string): void {
    if (priorityName !== this.priority?.name)
      this.newPriorityEvent.emit(priorityName);
  }
}
