import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KanbanRoutingModule } from './kanban-routing.module';
import { KanbanComponent } from './kanban.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SidebarComponent } from 'src/app/components/sidebar/sidebar.component';
import { LoadingComponent } from 'src/app/components/loading/loading.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [KanbanComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    KanbanRoutingModule,
    SidebarComponent,
    LoadingComponent,
  ],
})
export class KanbanModule {}
