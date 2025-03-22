import { TestBed } from '@angular/core/testing';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { UserService } from './user.service';
import { provideHttpClient } from '@angular/common/http';
import { Module } from '../models/Module.model';
import { Page } from '../models/Page.model';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  const apiUrl = `http://localhost:8000/user`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // test getPages

  it('getPages debe retornar un array de modulos en forma de Observable', (done: DoneFn) => {
    const testPages: Page[] = [
      { id: 1, name: 'testPage', path: '/pageTest', isAvailable: true },
      { id: 2, name: 'testPage2', path: '/pageTest2', isAvailable: true },
    ];

    const testModules: Module[] = [
      {
        name: 'test1',
        pages: testPages,
      },
    ];

    service.getPages(1).subscribe((value) => {
      expect(value.length).toBe(1);
      done();
    });

    const req = httpMock.expectOne(`/user/pages/1`);
    expect(req.request.method).toBe('GET');
    req.flush(testModules);
  });
  
  // test getUserInfo

  it('getUserInfo debe retornar la info del usuario en formato de Observable ', (done: DoneFn) => {
    const userInfo = {
      id: 1,
      name: "test name",
      email: "test@gmail.com"
    }

    service.getUserInfo(userInfo.id).subscribe((value) => {
      expect(value).toEqual(userInfo);
      done();
    })

    const req = httpMock.expectOne(`/user/info/${userInfo.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(userInfo);
  });


});
