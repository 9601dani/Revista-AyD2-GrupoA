import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarComponent } from './navbar.component';
import { provideHttpClient } from '@angular/common/http';
import { UserService } from '../../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;
  let mockUserService: any;
  let mockRouter: any;

  beforeEach(async () => {
    mockUserService = {
      getPages: jasmine.createSpy(),
      url: '/dashboard',
    };

    mockRouter = {
      navigate: jasmine.createSpy(),
      url: '/dashboard',
    };

    await TestBed.configureTestingModule({
      imports: [NavbarComponent],
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: (key: string) => 'mockValue' } },
            params: of({ id: '123' }),
          },
        },
        {
          provide: UserService,
          useValue: mockUserService,
        },
        {
          provide: Router,
          useValue: mockRouter,
        },
        
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('toggle navbar cambia de state', () => {
    component.isActive = false;
    component.toggleNavbar();
    expect(component.isActive).toBeTrue();
  });

  it('toggle submenu cambia su estado', () => {
    component.activeModule = 'mod1';
    component.toggleSubmenu('mod1');
    expect(component.activeModule).toBeNull();

    component.toggleSubmenu('mod2');
    expect(component.activeModule).toBe('mod2');
  });

  it('El componente debe navegar al perfil del usuario', () => {
    component.myAccount();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/user/profile']);
  });

  it('logout tiene que redirigir al login', () => {
    component.logout();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('showOptions debe mostrar boton de login', () => {
    mockRouter.url = '/login';
    component.showOptions();
    expect(component.showButtons).toBeFalse();
  });

  it('showButtons debe mostrar el navbar de alguien loggeado', () => {
    mockRouter.url = '/dashboard';
    component.showOptions();
    expect(component.showButtons).toBeTrue(); 
  });

});
