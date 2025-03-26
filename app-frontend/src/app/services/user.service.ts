import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { LocalStorageService } from './local-storage.service';
import {Module} from '../models/Module.model'
 
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Magazine } from '../models/Magazine.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  readonly apiUser = `${environment.API_URL}/user`;
  readonly apiMagazine = `${environment.API_URL}/v1/magazines`;

  constructor(private http: HttpClient, private _localStorage: LocalStorageService) { }

  getPages(id: number): Observable<Module[]> {
    return this.http.get<Module[]>(`${this.apiUser}/pages/${id}`);
  }

  getUserInfo(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUser}/info/${userId}`);
  }


  /* MAGAZINES */
  saveMagazine(magazine:Magazine):Observable<any>{
    return this.http.post<any>(`${this.apiMagazine}/save`,magazine)
  }

  
}
