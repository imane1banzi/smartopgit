import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CRComponent } from './cr.component';

describe('CRComponent', () => {
  let component: CRComponent;
  let fixture: ComponentFixture<CRComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CRComponent]
    });
    fixture = TestBed.createComponent(CRComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
