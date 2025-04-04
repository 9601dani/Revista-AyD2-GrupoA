import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAdsComponent } from './manage-ads.component';

describe('ManageAdsComponent', () => {
  let component: ManageAdsComponent;
  let fixture: ComponentFixture<ManageAdsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageAdsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageAdsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
