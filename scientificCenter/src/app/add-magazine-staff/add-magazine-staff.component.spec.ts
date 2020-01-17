import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMagazineStaffComponent } from './add-magazine-staff.component';

describe('AddMagazineStaffComponent', () => {
  let component: AddMagazineStaffComponent;
  let fixture: ComponentFixture<AddMagazineStaffComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddMagazineStaffComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddMagazineStaffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
