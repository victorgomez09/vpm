import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router,
} from '@angular/router';
import { Observable, of, Subscription } from 'rxjs';
import { catchError, first, map } from 'rxjs/operators';
import { AuthService } from '../../services/auth/auth.service';
import { handleError } from '../../../shared/utils/exception.util';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): Observable<boolean> {
    return this.authService.isAuthenticated().pipe(map(data => {
      if (data && data.token) { 
        this.authService.getLoggedUser()
        return true; 
      }
      this.router.navigate(['/login'])
      return false
      // return true
    }))
    // return this.authService.user$.pipe(map((data) => {
    //   console.log('data from user$', data)
    //   if (data && data.id) return true
    //   this.router.navigate(['/login']);
    //   return false;
    // }), catchError(() => {
    //   handleError<boolean>('authGuard', false)
    //   this.router.navigate(['/login']);
    //   return of(false);
    // }))
  }
}
