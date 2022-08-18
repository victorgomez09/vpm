import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  toggleNavbar: boolean = false;

  toggleNavbarClick(): void {
    this.toggleNavbar = !this.toggleNavbar;
  }
}
