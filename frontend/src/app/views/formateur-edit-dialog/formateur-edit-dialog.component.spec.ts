import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormateurEditDialogComponent } from './formateur-edit-dialog.component';

describe('FormateurEditDialogComponent', () => {
  let component: FormateurEditDialogComponent;
  let fixture: ComponentFixture<FormateurEditDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormateurEditDialogComponent]
    });
    fixture = TestBed.createComponent(FormateurEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
