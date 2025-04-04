import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuscriptionReportComponent } from './suscription-report.component';

describe('SuscriptionReportComponent', () => {
  let component: SuscriptionReportComponent;
  let fixture: ComponentFixture<SuscriptionReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SuscriptionReportComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuscriptionReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
