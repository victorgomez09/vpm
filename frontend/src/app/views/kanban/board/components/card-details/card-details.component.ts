import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { User } from 'src/app/models/auth.model';
import { Card, Column, UpdateCard } from 'src/app/models/kanban.model';
import { KanbanService } from 'src/app/services/kanban/kanban.service';
import { getInitials } from 'src/app/utils/text.util';
import { PriorityDropdownComponent } from '../priority-dropdown/priority-dropdown.component';
import { UsersDropdownComponent } from '../users-dropdown/users-dropdown.component';

declare const window: any;

@Component({
  standalone: true,
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.scss'],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    UsersDropdownComponent,
    PriorityDropdownComponent,
  ],
})
export class CardDetailsComponent implements OnInit {
  modal: any;
  card!: Card;
  editName: boolean;
  name?: string;
  editDescription: boolean;
  description?: string;
  columnsSubject!: Column[];

  constructor(
    private route: ActivatedRoute,
    private kanbanService: KanbanService
  ) {
    this.editName = false;
    this.editDescription = false;
  }

  ngOnInit(): void {
    this.modal = new window.bootstrap.Modal('#cardModal');
    this.modal.show();
    this.route.params.subscribe((params) => {
      this.kanbanService.getCardById(params['id']).subscribe((data) => {
        this.card = data;
        this.name = data.name;
        this.description = data.description;
      });
    });
    this.kanbanService.columns$.subscribe((data) => {
      this.columnsSubject = data;
    });
  }

  onClose(): void {
    this.modal.hide();
  }

  handleEditName(): void {
    this.editName = !this.editName;
  }

  handleEditDescription(): void {
    this.editDescription = !this.editDescription;
  }

  submitName(): void {
    const payload = {
      name: this.name!,
      description: this.card.description,
      users: this.card.users,
      priorityName: this.card.priority.name,
    };
    this.kanbanService.updateCard(this.card.id, payload).subscribe((data) => {
      this.updateCardFromSubject(data);
      this.card.name = data.name;
      this.editName = false;
    });
  }

  submitDescription(): void {
    const payload = {
      name: this.card.name,
      description: this.description!,
      users: this.card.users,
      priorityName: this.card.priority.name,
    };
    this.kanbanService.updateCard(this.card.id, payload).subscribe((data) => {
      this.updateCardFromSubject(data);
      this.card.description = data.description;
      this.editDescription = false;
    });
  }

  addUser(user: User): void {
    const users = this.card.users;
    const index = this.card.users.findIndex((e) => e.id === user.id);
    if (index === -1) users.push(user);
    else users.splice(index, 1);
    const payload: UpdateCard = {
      name: this.card.name,
      description: this.card.description,
      users,
      priorityName: this.card.priority.name,
    };
    this.kanbanService.updateCard(this.card.id, payload).subscribe((data) => {
      this.updateCardFromSubject(data);
    });
  }

  changePriority(priorityName: string) {
    const payload = {
      name: this.card.name,
      description: this.card.description,
      users: this.card.users,
      priorityName,
    };
    this.kanbanService.updateCard(this.card.id, payload).subscribe((data) => {
      this.updateCardFromSubject(data);
      this.card.priority = data.priority;
      this.editName = false;
    });
  }

  getInitials(text: string): string {
    return getInitials(text);
  }

  private updateCardFromSubject(data: Card) {
    this.columnsSubject.forEach((element) => {
      if (element.id === data.columnId) {
        const index = element.cards.findIndex((e) => e.id === data.id);
        element.cards.splice(index, 1);
        element.cards = [
          ...element.cards.slice(0, index),
          data,
          ...element.cards.slice(index),
        ];
      }
    });
    this.kanbanService.setColumnArraySubject(this.columnsSubject!);
  }
}
