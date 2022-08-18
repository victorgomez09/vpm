import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { RouterModule } from '@angular/router';
import { AppTemplateComponent } from './app-template/app-template.component';

@NgModule({
  declarations: [
    NavbarComponent,
    AppTemplateComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [NavbarComponent]
})
export class ComponentsModule { }
