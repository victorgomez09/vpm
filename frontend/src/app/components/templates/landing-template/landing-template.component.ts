import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-landing-template',
  templateUrl: './landing-template.component.html',
  styleUrls: ['./landing-template.component.scss'],
  imports: [RouterModule],
})
export class LandingTemplateComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
