import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/auth.model';
import {
  Board,
  CreateProject,
  Project,
} from 'src/app/models/issue-tracker.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { KanbanService } from 'src/app/services/kanban/kanban.service';
import { getFirstWordOfString, getInitials } from 'src/app/utils/text.util';

@Component({
  selector: 'app-kanban',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss'],
})
export class ProjectComponent implements OnInit {
  loading: boolean;
  projects: Project[];
  filteredProjects: Project[];
  user!: User;
  firstName!: string;
  projectForm: FormGroup;
  filterText: string;
  projectBackgroundColor: string;
  projectGeneratedCode: string;
  createdBoard: boolean;

  constructor(
    private fb: FormBuilder,
    private kanbanService: KanbanService,
    private authService: AuthService
  ) {
    this.loading = true;
    this.projects = [];
    this.filteredProjects = [];
    this.projectForm = fb.group({
      code: ['', Validators.required],
      name: ['', Validators.required],
      description: '',
      type: ['', Validators.required],
    });
    this.filterText = '';
    this.projectBackgroundColor = 'light';
    this.projectGeneratedCode = '';
    this.createdBoard = false;
  }

  ngOnInit(): void {
    this.authService.user$.subscribe((data) => {
      this.user = data;
      this.firstName = getFirstWordOfString(data.fullname);
      this.kanbanService.getProjects(data.id).subscribe((data) => {
        this.projects = data;
        console.log('projects', data);
        this.filteredProjects = data;
        this.loading = false;
      });
    });
  }

  createProject(): void {
    const data: CreateProject = {
      name: this.projectForm.value.name,
      code: this.projectForm.value.code,
      description: this.projectForm.value.description,
      color: this.projectBackgroundColor,
      users: [this.user.id],
      responsible: this.user.id,
      type: this.projectForm.value.type,
    };
    console.log({ ...data });

    this.kanbanService.createProject(data).subscribe((data) => {
      this.projects.push(data);
      this.createdBoard = true;
    });
  }

  handleChangeColor(newColor: string): void {
    this.projectBackgroundColor = newColor;
  }

  resetForm(): void {
    this.createdBoard = false;
    this.projectForm.reset();
  }

  search(): void {
    this.filteredProjects =
      this.filterText === ''
        ? this.projects
        : this.projects.filter((element) => {
            return element.name
              .toLowerCase()
              .includes(this.filterText.toLowerCase());
          });
  }

  generateCode(): void {
    this.projectForm.controls['code'].setValue(
      getInitials(this.projectForm.value.name)
    );
  }

  get f() {
    return this.projectForm.controls;
  }
}
