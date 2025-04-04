import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PaymentReportComponent } from './payment-report.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { UserService } from '../../../services/user.service';
import { ActivatedRoute } from '@angular/router';

describe('PaymentReportComponent', () => {
  let component: PaymentReportComponent;
  let fixture: ComponentFixture<PaymentReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentReportComponent, HttpClientTestingModule],
      providers: [
        UserService,
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

    fixture = TestBed.createComponent(PaymentReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
