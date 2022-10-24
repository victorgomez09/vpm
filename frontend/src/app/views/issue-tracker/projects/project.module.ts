import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KanbanRoutingModule } from './project-routing.module';
import { ProjectComponent } from './project.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SidebarComponent } from 'src/app/components/sidebar/sidebar.component';
import { LoadingComponent } from 'src/app/components/loading/loading.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [ProjectComponent],
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
export class ProjectModule {}
