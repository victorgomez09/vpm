import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth/auth.guard';
import { SharedModule } from './shared/shared.module';
import { AppTemplateComponent } from './shared/templates/app-template/app-template.component';
import { LandingTemplateComponent } from './shared/templates/landing-template/landing-template.component';
import { HomeComponent } from './views/home/home.component';
import { LoginComponent } from './views/login/login.component';
import { ProjectsComponent } from './views/projects/projects.component';
import { RegisterComponent } from './views/register/register.component';


const landingRoutes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
]

const appRoutes: Routes = [
  {
    path: 'projects',
    component: ProjectsComponent,
    canActivate: [AuthGuard]
  }
]

const routes: Routes = [
  {
    path: '',
    component: LandingTemplateComponent,
    children: landingRoutes
  },
  {
    path: '',
    component: AppTemplateComponent,
    children: appRoutes
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes), SharedModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
