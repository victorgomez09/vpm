import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { apiURL } from '../../constants/api.constant';
import { IUser } from '../../models/auth.model';
import { IProject } from '../../models/project.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class ProjectsService {
  user?: IUser;

  constructor(private http: HttpClient, private authService: AuthService) {}

  getProjects(userId: string): Observable<IProject[]> {
    console.log('userId', this.user?.id);
    return this.http.get<IProject[]>(
      `${apiURL}/projects/list?userId=${userId}`,
      {
        headers: {
          Authorization: `Bearer ${this.authService.getTokenFromStorage()}`,
        },
      }
    );
  }
}
