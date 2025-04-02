import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewMagazineModalComponent } from './preview-magazine-modal.component';

describe('PreviewMagazineModalComponent', () => {
  let component: PreviewMagazineModalComponent;
  let fixture: ComponentFixture<PreviewMagazineModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PreviewMagazineModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PreviewMagazineModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
