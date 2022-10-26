import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth/auth.guard';

const landingRoutes: Routes = [
  {
    path: '',
    loadChildren: () => import('./views').then((m) => m.LandingModule),
  },
  {
    path: 'login',
    loadChildren: () => import('./views').then((m) => m.LoginModule),
  },
  {
    path: 'register',
    loadChildren: () => import('./views').then((m) => m.RegisterModule),
  },
];

const appRoutes: Routes = [
  {
    path: 'dashboard',
    loadChildren: () => import('./views').then((m) => m.DashboardModule),
    canActivate: [AuthGuard],
  },
  {
    path: '',
    canActivate: [AuthGuard],
    children: [
      {
        path: 'kanban',
        loadChildren: () => import('./views').then((m) => m.KanbanModule),
        canActivate: [AuthGuard],
      },
      {
        path: 'kanban/board/:id',
        loadChildren: () => import('./views').then((m) => m.BoardModule),
        canActivate: [AuthGuard],
      },
    ],
  },
];

const routes: Routes = [
  {
    path: '',
    children: landingRoutes,
  },
  {
    path: '',
    loadComponent: () =>
      import('./components/templates').then((m) => m.AppTemplateComponent),
    children: appRoutes,
  },
  {
    path: '**',
    loadChildren: () => import('./views').then((m) => m.NotFoundModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  providers: [AuthGuard],
  exports: [RouterModule],
})
export class AppRoutingModule {}
