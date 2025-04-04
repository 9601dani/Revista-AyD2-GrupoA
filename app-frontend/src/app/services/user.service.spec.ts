// 👇 Asegúrate de importar correctamente todo
import { TestBed } from '@angular/core/testing';
import { UserService } from './user.service';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { Page } from '../models/Page.model';
import { environment } from '../../environments/environment';
import { UserInformation } from '../models/UserInformation.Model';
import { Module } from '../models/Module.model';
import { provideHttpClient } from '@angular/common/http';
import { Label } from '../models/Label.model';
import { Magazine, MagazineType } from '../models/Magazine.model';

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

    const req = httpMock.expectOne(
      `${apiUrl}/v1/users/info/update/photo_path/${id}`
    );
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
      current_balance: 10,
    };

    service.updateCurrentBalance(body).subscribe((value) => {
      expect(value).toEqual(userInfo);
      done();
    });

    const req = httpMock.expectOne(
      `${apiUrl}/v1/users/info/update/current_balance`
    );
    expect(req.request.method).toBe('PUT');
    req.flush(userInfo);
  });

  // Labels

  it('getAllLabels debe devolver un observable de tipo Label[] ', (done: DoneFn) => {
    const labels: Label[] = [
      {
        id: 1,
        name: 'label1',
      },
      {
        id: 2,
        name: 'label2',
      },
    ];

    service.getAllLabels().subscribe((value) => {
      expect(value).toEqual(labels);
      done();
    });

    const req = httpMock.expectOne(`${apiUrl}/v1/users/labels/all`);
    expect(req.request.method).toBe('GET');
    req.flush(labels);
  });
  
  it('getlabelsForUser debe devolver un observable de tipo Label[] ', (done: DoneFn) => {
    const labels: Label[] = [
      {
        id: 1,
        name: 'label1',
      },
      {
        id: 2,
        name: 'label2',
      },
    ];

    const fkUser: number = 1;

    service.getlabelsForUser(fkUser).subscribe((value) => {
      expect(value).toEqual(labels);
      done();
    });

    const req = httpMock.expectOne(`${apiUrl}/v1/users/labels/${fkUser}`);
    expect(req.request.method).toBe('GET');
    req.flush(labels);
  });
  
  it('savelLabelsToUser debe devolver un observable de tipo any ', (done: DoneFn) => {
    const labels: Label[] = [
      {
        id: 1,
        name: 'label1',
      },
      {
        id: 2,
        name: 'label2',
      },
    ];

    const body = {
      fkUser: 1,
      labels
    }


    service.saveLabeslToUser(body).subscribe((value) => {
      expect(value).toEqual(labels);
      done();
    });

    const req = httpMock.expectOne(`${apiUrl}/v1/users/labels/save`);
    expect(req.request.method).toBe('POST');
    req.flush(labels);
  });
  
  // Magazines
  
  it('saveMagazine debe devolver un observable de tipo any', (done: DoneFn) => {
    const formData = new FormData();
    formData.append('name', 'Revista de prueba');
    formData.append('description', 'Descripción');
    formData.append('canComment', 'true');
    formData.append('canLike', 'true');
    formData.append('canSubscribe', 'true');
    formData.append('FK_User', '1');
    formData.append('isEnabled', 'true');
    formData.append('price', '50');
    formData.append('type', 'FREE');
  
    const file = new File(['contenido'], 'revista.pdf', { type: 'application/pdf' });
    formData.append('file', file);
  
    const response = {
      id: 1,
      name: 'Revista de prueba',
      description: 'Descripción',
      canComment: true,
      canLike: true,
      canSubscribe: true,
      FK_User: 1,
      isEnabled: true,
      price: 50,
      path: 'path',
      type: 'FREE',
    };
  
    service.saveMagazine(formData).subscribe((res) => {
      expect(res).toEqual(response);
      done();
    });
  
    const req = httpMock.expectOne(`${apiUrl}/v1/magazines/save`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body instanceof FormData).toBeTrue();
    req.flush(response);
  });
  
  it('getMagazineByIdUser debe devolver un observable de tipo any ', (done: DoneFn) => {
    const id = 1;
  
    const magazine: Magazine = {
      id: 1,
      canComment: true,
      canLike: true,
      canSubscribe: true,
      description: "description",
      FK_User: 1,
      isEnabled: true,
      name: "magazine",
      path: "path",
      price: 50,
      type: MagazineType.FREE
    }
  
    const magazines: Magazine[] = [magazine];
  
    service.getMagazineByIdUser(id).subscribe((value) => {
      expect(value).toEqual(magazines);
      done();
    });
  
    const req = httpMock.expectOne(`${apiUrl}/v1/magazines/getByUserId/${id}`);
    expect(req.request.method).toBe('GET');
    req.flush(magazines);
  });
  
  
  it('getMagazineById debe devolver un observable de tipo any ', (done: DoneFn) => {
    
    const id = 1;

    const magazine: Magazine = {
      id: 1,
      canComment: true,
      canLike: true,
      canSubscribe: true,
      description: "description",
      FK_User: 1,
      isEnabled: true,
      name: "magazine",
      path: "path",
      price: 50,
      type: MagazineType.FREE
    }

    service.getMagazineById(id).subscribe((value) => {
      expect(value).toEqual(magazine);
      done();
    });

    const req = httpMock.expectOne(`${apiUrl}/v1/magazines/get/${id}`);
    expect(req.request.method).toBe('GET');
    req.flush(magazine);
  });


});
