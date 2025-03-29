import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddMagazineComponent } from './add-magazine.component';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { UserService } from '../../../services/user.service';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('AddMagazineComponent', () => {
  let component: AddMagazineComponent;
  let fixture: ComponentFixture<AddMagazineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddMagazineComponent],
      providers: [
        UserService,
        provideHttpClient(),
        provideHttpClientTesting(),
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ id: '1' }),
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AddMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
