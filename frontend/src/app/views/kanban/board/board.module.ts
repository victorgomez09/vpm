import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BoardRoutingModule } from './board-routing.module';
import { BoardComponent } from './board.component';
import { SidebarComponent } from 'src/app/components/sidebar/sidebar.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ColumnComponent } from './components/column/column.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { ModalComponent } from './components/modal/modal.component';

@NgModule({
  declarations: [BoardComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DragDropModule,
    BoardRoutingModule,
    SidebarComponent,
    ColumnComponent,
    ModalComponent,
  ],
})
export class BoardModule {}
