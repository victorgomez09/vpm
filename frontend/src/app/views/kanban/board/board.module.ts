import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BoardRoutingModule } from './board-routing.module';
import { BoardComponent } from './board.component';
import { SidebarComponent } from 'src/app/components/sidebar/sidebar.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ColumnComponent } from './components/column/column.component';
import { DragDropModule } from '@angular/cdk/drag-drop';

@NgModule({
  declarations: [BoardComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DragDropModule,
    BoardRoutingModule,
    SidebarComponent,
    ColumnComponent,
  ],
})
export class BoardModule {}
