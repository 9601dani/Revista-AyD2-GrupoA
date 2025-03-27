import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { LocalStorageService } from './local-storage.service';
import {Module} from '../models/Module.model'

import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { UserInformation } from '../models/UserInformation.Model';
import { Magazine } from '../models/Magazine.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  readonly apiMagazine = `${environment.API_URL}/v1/magazines`;
  readonly apiUser = `${environment.API_URL}/v1/users`;

  constructor(private http: HttpClient, private _localStorage: LocalStorageService) { }

  getPages(id: number): Observable<Module[]> {
    return this.http.get<Module[]>(`${this.apiUser}/pages/${id}`);
  }

   getUserInfo(userId: number): Observable<any> {
    return this.http.get<UserInformation>(`${this.apiUser}/info/${userId}`);
  }


  /* MAGAZINES */
  saveMagazine(magazine:Magazine, file:File):Observable<any>{
    const formData = new FormData();
    formData.append('magazine', new Blob([JSON.stringify(magazine)], { type: 'application/json' }));

    formData.append('file', file);

    return this.http.post<any>(`${this.apiMagazine}/save`, formData);
  }

  getMagazineByIdUser(id:number):Observable<any>{
    return this.http.get<any>(`${this.apiMagazine}/getByIdUser/${id}`)
  }

  getMagazineById(id:number):Observable<any>{
    return this.http.get<any>(`${this.apiMagazine}/get/${id}`)
  }


}
