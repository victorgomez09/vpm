import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersDropdown } from './users-dropdown.component';

describe('SearchUsersComponent', () => {
  let component: UsersDropdown;
  let fixture: ComponentFixture<UsersDropdown>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UsersDropdown],
    }).compileComponents();

    fixture = TestBed.createComponent(UsersDropdown);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
