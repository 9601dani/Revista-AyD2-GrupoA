import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdContainerComponent } from './ad-container.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AdsService } from '../../../services/ads/ads.service';
import { LocalStorageService } from '../../../services/local-storage.service';

describe('AdContainerComponent', () => {
  let component: AdContainerComponent;
  let fixture: ComponentFixture<AdContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdContainerComponent, HttpClientTestingModule],
      providers: [
        AdsService,
        {
          provide: LocalStorageService,
          useValue: {
            USER_ID: 'user_id',
            getItem: () => '1',
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AdContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
