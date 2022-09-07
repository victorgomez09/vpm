import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { LandingComponent } from './landing/landing.component';
import { SharedModule } from '../shared/shared.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { BoardComponent } from './kanban/board/board.component';
import { KanbanComponent } from './kanban/kanban/kanban.component';

@NgModule({
  declarations: [
    LandingComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    NotFoundComponent,
    KanbanComponent,
    BoardComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule,
    ReactiveFormsModule,
    DragDropModule
  ],
  exports: [
    LandingComponent, 
    LoginComponent, 
    RegisterComponent, 
    DashboardComponent, 
    KanbanComponent,
    BoardComponent,
    NotFoundComponent
  ]
})
export class ViewsModule { }
