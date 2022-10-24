import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { User } from '../../models/auth.model';
import { getFirstWordOfString, getInitials } from '../../utils/text.util';

@Component({
  standalone: true,
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  imports: [CommonModule, RouterModule],
})
export class NavbarComponent implements OnInit {
  navbarToggle: boolean = false;
  user?: User;
  firstName?: string;
  initials?: string;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.user$.subscribe((data) => {
      this.user = data;
      this.firstName = getFirstWordOfString(data.fullname);
      this.initials = getInitials(data.fullname);
    });
  }

  handleNavbarToggler(): void {
    this.navbarToggle = !this.navbarToggle;
  }
}
