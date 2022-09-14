import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Card } from 'src/app/models/kanban.model';
import { KanbanService } from 'src/app/services/kanban/kanban.service';

declare const window: any;

@Component({
  standalone: true,
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
  imports: [CommonModule, RouterModule],
})
export class ModalComponent implements OnInit {
  modal: any;
  card!: Card;
  editDescription: boolean;

  constructor(
    private route: ActivatedRoute,
    private kanbanService: KanbanService
  ) {
    this.editDescription = false;
  }

  ngOnInit(): void {
    this.modal = new window.bootstrap.Modal('#cardModal');
    this.modal.show();
    this.route.params.subscribe((params) => {
      console.log('params', params['id']);
      this.kanbanService
        .getCardById(params['id'])
        .subscribe((data) => (this.card = data));
    });
  }

  onClose(): void {
    this.modal.hide();
  }

  handleEditDescription(): void {
    this.editDescription = !this.editDescription;
  }
}
