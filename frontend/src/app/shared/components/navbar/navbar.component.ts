import { Component, OnInit } from '@angular/core';
import { IUser } from '../../../core/models/auth.model';
import { AuthService } from '../../../core/services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  toggleNavbar: boolean = false;
  user?: IUser

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.user.subscribe(data => this.user = data!);
  }

  handleToggleNavbar(): void {
    this.toggleNavbar = !this.toggleNavbar;
  }
}
