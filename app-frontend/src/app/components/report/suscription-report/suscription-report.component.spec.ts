import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { SuscriptionReportComponent } from './suscription-report.component';
import { UserService } from '../../../services/user.service';
import { LocalStorageService } from '../../../services/local-storage.service';
import { ActivatedRoute } from '@angular/router';

describe('SuscriptionReportComponent', () => {
  let component: SuscriptionReportComponent;
  let fixture: ComponentFixture<SuscriptionReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SuscriptionReportComponent, HttpClientTestingModule],
      providers: [
        UserService,
        {
          provide: LocalStorageService,
          useValue: {
            USER_ID: 'user_id',
            getItem: () => '1',
          },
        },
        {
          provide: ActivatedRoute,
          useValue: {
            params: {
              subscribe: () => {}
            }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(SuscriptionReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
