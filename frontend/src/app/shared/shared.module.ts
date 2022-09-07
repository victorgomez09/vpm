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

@NgModule({
  declarations: [
    NavbarComponent,
    LandingTemplateComponent,
    AppTemplateComponent,
    LoadingComponent,
    ColumnComponent,
    CardComponent,
  ],
  imports: [CommonModule, RouterModule, HttpClientModule, DragDropModule],
  exports: [
    LandingTemplateComponent,
    AppTemplateComponent,
    NavbarComponent,
    LoadingComponent,
    ColumnComponent,
    CardComponent
  ],
})
export class SharedModule {}
