import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PreviewMagazineModalComponent } from './preview-magazine-modal.component';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

describe('PreviewMagazineModalComponent', () => {
  let component: PreviewMagazineModalComponent;
  let fixture: ComponentFixture<PreviewMagazineModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PreviewMagazineModalComponent],
      providers: [
        {
          provide: MAT_DIALOG_DATA,
          useValue: {
            categories: [{ name: 'ciencia' }, { name: 'arte' }],
            labels: [{ name: 'interesante' }, { name: 'visual' }]
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(PreviewMagazineModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
