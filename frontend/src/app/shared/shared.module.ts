import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { LandingTemplateComponent } from './templates/landing-template/landing-template.component';
import { AppTemplateComponent } from './templates/app-template/app-template.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ColumnComponent } from './components/kanban/column/column.component';
import { CardComponent } from './components/kanban/card/card.component';
import { LoadingComponent } from './components/loading/loading.component';
import { ReactiveFormsModule } from '@angular/forms';
import { KanbanTemplateComponent } from './templates/kanban-template/kanban-template.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';

@NgModule({
  declarations: [
    NavbarComponent,
    LandingTemplateComponent,
    AppTemplateComponent,
    KanbanTemplateComponent,
    LoadingComponent,
    ColumnComponent,
    CardComponent,
    SidebarComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    DragDropModule,
    ReactiveFormsModule,
  ],
  exports: [
    LandingTemplateComponent,
    AppTemplateComponent,
    KanbanTemplateComponent,
    NavbarComponent,
    SidebarComponent,
    LoadingComponent,
    ColumnComponent,
    CardComponent,
  ],
})
export class SharedModule {}
