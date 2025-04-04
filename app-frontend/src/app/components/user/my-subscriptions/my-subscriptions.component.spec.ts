import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MySubscriptionsComponent } from './my-subscriptions.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { UserService } from '../../../services/user.service';
import { LocalStorageService } from '../../../services/local-storage.service';
import { Router, ActivatedRoute } from '@angular/router';

describe('MySubscriptionsComponent', () => {
  let component: MySubscriptionsComponent;
  let fixture: ComponentFixture<MySubscriptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MySubscriptionsComponent, HttpClientTestingModule],
      providers: [
        UserService,
        {
          provide: LocalStorageService,
          useValue: {
            USER_ID: 'user_id',
            EMAIL: 'email',
            ROLE: 'role',
            USER_NAME: 'username',
            getItem: (key: string) => {
              const store: Record<string, string> = {
                user_id: '1',
                email: 'test@example.com',
                role: 'USER,ADMIN',
                username: 'TestUser'
              };
              return store[key] ?? '';
            },
            logout: jasmine.createSpy('logout'),
          },
        },
        {
          provide: Router,
          useValue: {
            url: '/dashboard',
            navigate: jasmine.createSpy('navigate'),
          },
        },
        {
          provide: ActivatedRoute,
          useValue: {
            params: {
              subscribe: () => {},
            },
            snapshot: {
              paramMap: {
                get: () => null,
              },
            },
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MySubscriptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
