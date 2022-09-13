import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../navbar/navbar.component';

@Component({
  standalone: true,
  selector: 'app-app-template',
  templateUrl: './app-template.component.html',
  styleUrls: ['./app-template.component.scss'],
  imports: [RouterModule, NavbarComponent],
})
export class AppTemplateComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
