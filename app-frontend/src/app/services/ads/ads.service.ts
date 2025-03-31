import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdsService {

  readonly apiAds = `${environment.API_URL}/v1/ads`;
  readonly apiCategories = `${environment.API_URL}/v1/categories`;
  readonly apiPeriods = `${environment.API_URL}/v1/periods`;

  constructor(
    private httpClient: HttpClient
  ) { }

  getAllCategories(): Observable<any> {
    return this.httpClient.get(`${this.apiCategories}`);
  }

  getAllAdTypes(): Observable<any> {
    return this.httpClient.get(`${this.apiAds}/types`);
  }

  getAllPeriods(): Observable<any> {
    return this.httpClient.get(`${this.apiPeriods}`)
  }

  saveAd(data: FormData): Observable<any> {
    return this.httpClient.post(`${this.apiAds}`, data);
  }
}
