import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ComponentsModule } from './components/components.module';
import { AuthGuard } from './guards/auth/auth.guard';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule,
    ComponentsModule
  ],
  providers: [AuthGuard],
  exports: [ComponentsModule]
})
export class SharedModule { }
