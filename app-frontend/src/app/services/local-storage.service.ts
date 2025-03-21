import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  readonly TOKEN = 'token';
  readonly USER_ID = 'user_id';
  readonly USER_NAME = 'user_name';
  readonly USER_PHOTO = 'user_photo';
  readonly COMPANY_LOGO = 'company_logo';
  readonly COMPANY_NAME = 'company_name';
  readonly CURRENCY = 'currency';

  constructor() {}

  private isLocalStorageAvailable(): boolean {
    return typeof window !== 'undefined' && !!window.localStorage;
  }

  setItem(key: string, value: any): void {
    if (this.isLocalStorageAvailable()) {
      localStorage.setItem(key, JSON.stringify(value));
    }
  }

  getItem(key: string): any {
    if (this.isLocalStorageAvailable()) {
      const item = localStorage.getItem(key);
      return item ? JSON.parse(item) : null;
    }
    return null;
  }

  removeItem(key: string): void {
    if (this.isLocalStorageAvailable()) {
      localStorage.removeItem(key);
    }
  }

  clear(): void {
    if (this.isLocalStorageAvailable()) {
      localStorage.clear();
    }
  }

  getUserId(): number {
    return this.getItem(this.USER_ID);
  }

  setUserId(id: number): void {
    this.setItem(this.USER_ID, id);
  }

  getUserName(): string {
    return this.getItem(this.USER_NAME);
  }

  setUsername(name: string): void {
    this.setItem(this.USER_NAME, name);
  }

  setUserPhoto(photo: string): void {
    this.setItem(this.USER_PHOTO, photo);
  }

  getUserPhoto(): string {
    return this.getItem(this.USER_PHOTO);
  }

  logout(): void {
    this.clear();
    // this._cookieService.delete('token');
  }
}
