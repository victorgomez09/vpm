import { Component } from '@angular/core';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss'],
})
export class LandingComponent {
  toggleNavbar: boolean = false;

  toggleNavbarClick(): void {
    console.log('toggle navbar');
    this.toggleNavbar = !this.toggleNavbar;
  }
}
