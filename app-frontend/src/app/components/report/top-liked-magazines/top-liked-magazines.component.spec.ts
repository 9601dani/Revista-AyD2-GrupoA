import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopLikedMagazinesComponent } from './top-liked-magazines.component';

describe('TopLikedMagazinesComponent', () => {
  let component: TopLikedMagazinesComponent;
  let fixture: ComponentFixture<TopLikedMagazinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopLikedMagazinesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopLikedMagazinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
