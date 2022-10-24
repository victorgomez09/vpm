import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BoardRoutingModule } from './board-routing.module';
import { BoardComponent } from './board.component';
import { SidebarComponent } from 'src/app/components/sidebar/sidebar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ColumnComponent } from './components/column/column.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { CardDetailsComponent } from './components/card-details/card-details.component';
import { LoadingComponent } from 'src/app/components/loading/loading.component';

@NgModule({
  declarations: [BoardComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    DragDropModule,
    BoardRoutingModule,
    SidebarComponent,
    ColumnComponent,
    CardDetailsComponent,
    LoadingComponent,
  ],
})
export class BoardModule {}
