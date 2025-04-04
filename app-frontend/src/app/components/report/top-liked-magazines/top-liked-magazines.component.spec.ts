import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TopLikedMagazinesComponent } from './top-liked-magazines.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { importProvidersFrom } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

describe('TopLikedMagazinesComponent', () => {
  let component: TopLikedMagazinesComponent;
  let fixture: ComponentFixture<TopLikedMagazinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopLikedMagazinesComponent],
      providers: [
        importProvidersFrom(HttpClientTestingModule),
        {
          provide: ActivatedRoute,
          useValue: {
            params: { subscribe: () => {} },
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(TopLikedMagazinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
