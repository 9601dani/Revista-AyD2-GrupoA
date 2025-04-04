import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CommentReportComponent } from './comment-report.component';
import { UserService } from '../../../services/user.service';
import { ActivatedRoute } from '@angular/router';

describe('CommentReportComponent', () => {
  let component: CommentReportComponent;
  let fixture: ComponentFixture<CommentReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        CommentReportComponent,
        HttpClientTestingModule
      ],
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

    fixture = TestBed.createComponent(CommentReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
