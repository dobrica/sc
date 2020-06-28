import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorTaskComponent } from './editor-task.component';

describe('EditorTaskComponent', () => {
  let component: EditorTaskComponent;
  let fixture: ComponentFixture<EditorTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorTaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
