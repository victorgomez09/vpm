import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ButtonComponent } from './components/button/button.component';
import { LoadingComponent } from './components/loading/loading.component';
import { LandingTemplateComponent } from './templates/landing-template/landing-template.component';
import { AppTemplateComponent } from './templates/app-template/app-template.component';

@NgModule({
  declarations: [
    NavbarComponent,
    ButtonComponent,
    LoadingComponent,
    LandingTemplateComponent,
    AppTemplateComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule
  ],
  exports: [LandingTemplateComponent, AppTemplateComponent, NavbarComponent, ButtonComponent, LoadingComponent]
})
export class SharedModule { }
