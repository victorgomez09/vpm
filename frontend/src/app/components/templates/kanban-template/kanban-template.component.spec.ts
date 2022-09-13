import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KanbanTemplateComponent } from './kanban-template.component';

describe('KanbanTemplateComponent', () => {
  let component: KanbanTemplateComponent;
  let fixture: ComponentFixture<KanbanTemplateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KanbanTemplateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KanbanTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
