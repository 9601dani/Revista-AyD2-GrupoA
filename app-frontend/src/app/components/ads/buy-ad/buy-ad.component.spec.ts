import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BuyAdComponent } from './buy-ad.component';
import { AdsService } from '../../../services/ads/ads.service';
import { UserService } from '../../../services/user.service';
import { LocalStorageService } from '../../../services/local-storage.service';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { ActivatedRoute } from '@angular/router';

describe('BuyAdComponent', () => {
  let component: BuyAdComponent;
  let fixture: ComponentFixture<BuyAdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuyAdComponent, HttpClientTestingModule],
      providers: [
        AdsService,
        UserService,
        {
          provide: LocalStorageService,
          useValue: {
            getItem: (key: string) => '1',
            USER_ID: 'user_id'
          }
        },
        {
          provide: LiveAnnouncer,
          useValue: {
            announce: jasmine.createSpy('announce')
          }
        },
        {
          provide: ActivatedRoute,
          useValue: {
            params: [],
            snapshot: {
              paramMap: {
                get: () => null
              }
            }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BuyAdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
