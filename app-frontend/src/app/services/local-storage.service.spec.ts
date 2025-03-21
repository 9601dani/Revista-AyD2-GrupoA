import { TestBed } from '@angular/core/testing';

import { LocalStorageService } from './local-storage.service';

describe('LocalStorageService', () => {
  let service: LocalStorageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocalStorageService);

    let store: any = {};
    const mockLocalStorage = {
      getItem: (key: string): string | null => {
        return key in store ? store[key] : null;
      },
      setItem: (key: string, value: string) => {
        store[key] = `${value}`;
      },
      removeItem: (key: string) => {
        delete store[key];
      },
      clear: () => {
        store = {};
      },
    };

    spyOn(localStorage, 'getItem').and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, 'setItem').and.callFake(mockLocalStorage.setItem);
    spyOn(localStorage, 'removeItem').and.callFake(mockLocalStorage.removeItem);
    spyOn(localStorage, 'clear').and.callFake(mockLocalStorage.clear);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get item of localStorage', () => {
    const testObject = {
      id: 1,
      name: 'test name',
    };

    localStorage.setItem('test_key', JSON.stringify(testObject));
    expect(service.getItem('test_key')).toEqual(testObject);
  });

  it('should set item of localStorage', () => {
    const testObject = {
      id: 1,
      name: 'test name',
    };

    service.setItem('test_key', testObject);
    expect(localStorage.getItem('test_key')).toEqual(
      JSON.stringify(testObject)
    );
  });

  it('should remove item of localStorage', () => {
    const testObject = {
      id: 1,
      name: 'test name',
    };

    localStorage.setItem('test_key', JSON.stringify(testObject));

    service.removeItem('test_key');

    expect(localStorage.getItem('test_key')).toBeNull();
  });

  it('should clear localStorage', () => {

    const testObject = {
      id: 1,
      name: 'test name',
    };

    localStorage.setItem('test_key', JSON.stringify(testObject));

    service.clear();

    expect(localStorage.getItem('test_key')).toBeNull();
  });
});
