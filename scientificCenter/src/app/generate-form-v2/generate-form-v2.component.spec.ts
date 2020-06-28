import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateFormV2Component } from './generate-form-v2.component';

describe('GenerateFormV2Component', () => {
  let component: GenerateFormV2Component;
  let fixture: ComponentFixture<GenerateFormV2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenerateFormV2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenerateFormV2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
