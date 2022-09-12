import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth/auth.guard';
import { SharedModule } from './shared/shared.module';
import { AppTemplateComponent } from './shared/templates/app-template/app-template.component';
import { LandingTemplateComponent } from './shared/templates/landing-template/landing-template.component';
import { DashboardComponent } from './views/dashboard/dashboard.component';
import { LandingComponent } from './views/landing/landing.component';
import { BoardComponent } from './views/kanban/board/board.component';
import { KanbanComponent } from './views/kanban/kanban/kanban.component';
import { LoginComponent } from './views/login/login.component';
import { NotFoundComponent } from './views/not-found/not-found.component';
import { RegisterComponent } from './views/register/register.component';
import { KanbanTemplateComponent } from './shared/templates/kanban-template/kanban-template.component';

const landingRoutes: Routes = [
  {
    path: '',
    component: LandingComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
];

const appRoutes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
  },
  {
    path: '',
    component: KanbanTemplateComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'kanban',
        component: KanbanComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'kanban/board/:id',
        component: BoardComponent,
        canActivate: [AuthGuard],
      },
    ],
  },
];

const routes: Routes = [
  {
    path: '',
    component: LandingTemplateComponent,
    children: landingRoutes,
  },
  {
    path: '',
    component: AppTemplateComponent,
    children: appRoutes,
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), SharedModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
