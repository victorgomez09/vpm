import { Component, Input, OnInit } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  @Input()
  title: string;
  @Input()
  subtitle: string;

  constructor() {
    this.title = '';
    this.subtitle = '';
  }

  ngOnInit(): void {}
}
