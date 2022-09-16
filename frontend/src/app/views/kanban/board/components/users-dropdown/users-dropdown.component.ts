import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { User } from 'src/app/models/auth.model';
import { UserService } from 'src/app/services/users/user.service';

@Component({
  standalone: true,
  selector: 'app-users-dropdown',
  templateUrl: './users-dropdown.component.html',
  styleUrls: ['./users-dropdown.component.scss'],
  imports: [CommonModule, FormsModule],
})
export class UsersDropdownComponent implements OnInit {
  @Input()
  excludeUsers: User[];
  @Output()
  newItemEvent: EventEmitter<User>;
  users!: User[];
  filteredUsers!: User[];
  filteredText: string;

  constructor(private userService: UserService) {
    this.excludeUsers = [];
    this.newItemEvent = new EventEmitter<User>();
    this.filteredText = '';
  }

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe((data) => {
      this.users = data;
      this.filteredUsers = data;
    });
  }

  addNewUser(user: User) {
    this.newItemEvent.emit(user);
  }

  isUserSelected(user: User): boolean {
    return this.excludeUsers.filter((e) => e.email === user.email).length > 0;
  }

  search(): void {
    this.filteredUsers =
      this.filteredText === ''
        ? this.users
        : this.users.filter((element) => {
            return element.fullname
              .toLowerCase()
              .includes(this.filteredText.toLowerCase());
          });
  }
}
