import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewScientificPaperComponent } from './new-scientific-paper.component';

describe('NewScientificPaperComponent', () => {
  let component: NewScientificPaperComponent;
  let fixture: ComponentFixture<NewScientificPaperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewScientificPaperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewScientificPaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
