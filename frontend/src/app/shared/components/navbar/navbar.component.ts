import { Component, OnInit } from '@angular/core';
import { User } from '../../../core/models/auth.model';
import { AuthService } from '../../../core/services/auth/auth.service';
import { getInitials } from '../../utils/text.util';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  toggleNavbar: boolean = false;
  user?: User
  initials?: string;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.user$.subscribe(data => {
        this.user = data
        this.initials = getInitials(data.fullname);
    });
  }

  handleToggleNavbar(): void {
    this.toggleNavbar = !this.toggleNavbar;
  }
}
