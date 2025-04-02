import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { environment } from '../../environments/environment';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  const apiUrl =  `${environment.API_URL}/v1/users`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(), provideHttpClientTesting(), AuthService
      ]
    });
    service = TestBed.inject(AuthService);
    
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('registerUser debe mandar los datos de usuario al backend', (done: DoneFn) => {
      const user = {
        id: 1,
        username: "test",
        email: "test@gmail.com",
        token: ""
      }
  
      service.registerUser(user).subscribe((value) => {
        expect(value).toEqual(user);
        done();
      });
  
      const req = httpMock.expectOne(`${apiUrl}/register`);
      expect(req.request.method).toBe('POST');
      req.flush(user);
    });
  
  
    it('login debe devover el usuario junto con un token', (done: DoneFn) => {
      const user = {
        id: 1,
        username: "test",
        email: "test@gmail.com",
        token: "token_test"
      }
  
      service.login(user).subscribe((value) => {
        expect(value).toEqual(user);
        done();
      });
  
      const req = httpMock.expectOne(`${apiUrl}/login`);
      expect(req.request.method).toBe('POST');
      req.flush(user);
    });


});
