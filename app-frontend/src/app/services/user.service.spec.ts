// 👇 Asegúrate de importar correctamente todo
import { TestBed } from '@angular/core/testing';
import { UserService } from './user.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { Page } from '../models/Page.model';
import { environment } from '../../environments/environment';
import { UserInformation } from '../models/UserInformation.Model';
import { Module } from '../models/Module.model';
import { provideHttpClient } from '@angular/common/http';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  const apiUrl = environment.API_URL;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
      providers: [UserService, provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getPages debe retornar un array de modulos en forma de Observable', (done: DoneFn) => {
    const testPages: Page[] = [
      { id: 1, name: 'testPage', path: '/pageTest', isEnabled: true },
      { id: 2, name: 'testPage2', path: '/pageTest2', isEnabled: true },
    ];

    const testModules: Module[] = [{ name: 'test1', pages: testPages }];

    service.getPages(1).subscribe((value) => {
      expect(value.length).toBe(1);
      done();
    });

    const req = httpMock.expectOne(`${apiUrl}/v1/users/pages/1`);
    expect(req.request.method).toBe('GET');
    req.flush(testModules);
  });

  it('getUserInfo debe retornar la info del usuario en formato de Observable ', (done: DoneFn) => {
    const userInfo: UserInformation = {
      id: 1,
      name: 'test name',
      age: 18,
      description: 'description test',
      fkUser: 1,
      photo_path: 'path',
      current_balance: 0,
    };

    service.getUserInfo(userInfo.id).subscribe((value) => {
      expect(value).toEqual(userInfo);
      done();
    });

    const req = httpMock.expectOne(`${apiUrl}/v1/users/info/${userInfo.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(userInfo);
  });

  it('updateUserInfo debe devolver un observable de tipo UserInformation ', (done: DoneFn) => {
    const userInfo: UserInformation = {
      id: 1,
      name: 'test name',
      age: 18,
      description: 'description test',
      fkUser: 1,
      photo_path: 'path',
      current_balance: 0,
    };

    service.updateUserInfo(userInfo).subscribe((value) => {
      expect(value).toEqual(userInfo);
      done();
    });

    const req = httpMock.expectOne(`${apiUrl}/v1/users/info/update`);
    expect(req.request.method).toBe('PUT');
    req.flush(userInfo);
  });

  it('updatePhotoPath debe devolver el path de la imagen del usuario ', (done: DoneFn) => {
    const path = {
      photo_path: 'path',
    };

    const id = 1;

    const content = 'Contenido';
    const blob = new Blob([content], { type: 'img' });
    const file = new File([blob], 'imagen.png', { type: 'img' });

    const formData = new FormData();
    formData.append('file', file);

    service.updatePhotoPath(formData, id).subscribe((value) => {
      expect(value).toEqual(path);
      done();
    });

    const req = httpMock.expectOne(`${apiUrl}/v1/users/info/update/photo_path/${id}`);
    expect(req.request.method).toBe('PUT');
    req.flush(path);
  });

  it('updateCurrentBalance debe devolver un observable de tipo UserInformation ', (done: DoneFn) => {
    const userInfo: UserInformation = {
      id: 1,
      name: 'test name',
      age: 18,
      description: 'description test',
      fkUser: 1,
      photo_path: 'path',
      current_balance: 0,
    };

    const body = {
      fkUser: 1,
      sum: true,
      current_balance: 10
    }

    service.updateCurrentBalance(body).subscribe((value) => {
      expect(value).toEqual(userInfo);
      done();
    });

    const req = httpMock.expectOne(`${apiUrl}/v1/users/info/update/current_balance`);
    expect(req.request.method).toBe('PUT');
    req.flush(userInfo);
  });
});
