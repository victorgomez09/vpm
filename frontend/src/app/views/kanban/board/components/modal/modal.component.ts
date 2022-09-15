import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { User } from 'src/app/models/auth.model';
import { Card, UpdateCard } from 'src/app/models/kanban.model';
import { KanbanService } from 'src/app/services/kanban/kanban.service';
import { getInitials } from 'src/app/utils/text.util';
import { UsersDropdown } from '../users-dropdown/users-dropdown.component';

declare const window: any;

@Component({
  standalone: true,
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
  imports: [CommonModule, RouterModule, FormsModule, UsersDropdown],
})
export class ModalComponent implements OnInit {
  modal: any;
  card!: Card;
  editName: boolean;
  name?: string;
  editDescription: boolean;
  description?: string;

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
    };
    this.kanbanService.updateCard(this.card.id, payload).subscribe((data) => {
      this.card = data;
      this.editName = false;
    });
  }

  submitDescription(): void {
    const payload = {
      name: this.card.name,
      description: this.description!,
      users: this.card.users,
    };
    this.kanbanService.updateCard(this.card.id, payload).subscribe((data) => {
      this.card = data;
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
    };
    this.kanbanService.updateCard(this.card.id, payload).subscribe((data) => {
      this.card.users = data.users;
    });
  }

  getInitials(text: string): string {
    return getInitials(text);
  }
}
