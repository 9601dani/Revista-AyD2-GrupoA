import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditMagazineComponent } from './edit-magazine.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { LocalStorageService } from '../../../services/local-storage.service';
import { UserService } from '../../../services/user.service';
import { FormBuilder } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';

describe('EditMagazineComponent', () => {
  let component: EditMagazineComponent;
  let fixture: ComponentFixture<EditMagazineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditMagazineComponent, HttpClientTestingModule],
      providers: [
        FormBuilder,
        DomSanitizer,
        LocalStorageService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ id: 1 })
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(EditMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
