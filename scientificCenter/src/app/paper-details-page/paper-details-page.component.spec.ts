import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaperDetailsPageComponent } from './paper-details-page.component';

describe('PaperDetailsPageComponent', () => {
  let component: PaperDetailsPageComponent;
  let fixture: ComponentFixture<PaperDetailsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaperDetailsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaperDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
