import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyAdComponent } from './buy-ad.component';

describe('BuyAdComponent', () => {
  let component: BuyAdComponent;
  let fixture: ComponentFixture<BuyAdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuyAdComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuyAdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
