import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { IUser } from 'src/app/core/models/auth.model';
import { IProject } from 'src/app/core/models/project.model';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { ProjectsService } from 'src/app/core/services/projects/projects.service';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {
  user?: IUser;
  projects?: IProject[]
  loading?: boolean;

  constructor(private authService: AuthService, private projectService: ProjectsService) { 
    this.loading = true;
  }
  
  ngOnInit(): void {
    this.authService.user$.subscribe((data) => {
      if (data) {
        console.log('user subscribe')
        this.user = data!
        this.projectService.getProjects().subscribe(data => this.projects = data);
        this.loading = false
      }
    });
  }

}
