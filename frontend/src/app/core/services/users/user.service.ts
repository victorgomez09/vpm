// import { HttpClient } from '@angular/common/http';
// import { Injectable } from '@angular/core';
// import { BehaviorSubject, Observable } from 'rxjs';
// import { apiURL } from '../../constants/api.constant';
// import { IUser } from '../../models/auth.model';
// import { AuthService } from '../auth/auth.service';

// @Injectable({
//   providedIn: 'root'
// })
// export class UserService {
//   private userSubject: BehaviorSubject<User>;
//   public user$: Observable<User | null>;

//   constructor(private http: HttpClient, private authService: AuthService) {
//     this.userSubject = new BehaviorSubject<User | null>(null);
//     this.user$ = this.userSubject.asObservable();
//     this.getUser()
//   }

//   getUser(): void {
//     if (this.authService.getTokenFromStorage()) {
//       console.log('fetching user');
//       this.http.get<User>(`${apiURL}/auth/get-user?token=${this.authService.getTokenFromStorage()}`, {
//         headers: {
//           'Authorization': `Bearer ${this.authService.getTokenFromStorage()}`
//         }
//       }).subscribe(data => {
//         this.userSubject.next(data)
//         console.log('next called');
//       });
//     } else {
//       console.log('setting null subject');
//       this.userSubject.next(null);
//     }
//   }

//   public get currentUserValue(): User {
//     return this.userSubject.value;
// }
// }
