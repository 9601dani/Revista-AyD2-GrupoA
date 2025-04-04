import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {interval, Observable, shareReplay, startWith, switchMap} from 'rxjs';
import { Period } from '../../models/Period.mode';

@Injectable({
  providedIn: 'root'
})
export class AdsService {

  readonly apiAds = `${environment.API_URL}/v1/ads`;
  readonly apiCategories = `${environment.API_URL}/v1/temp-categories`;
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
    
  saveAllPeriods(formData: Period[]):Observable<any>{
      return this.httpClient.post(`${this.apiPeriods}/save`,formData)
  }

  saveAd(data: FormData): Observable<any> {
    return this.httpClient.post(`${this.apiAds}`, data);
  }

  getRandomAd(id: any): Observable<any> {
    return this.httpClient.get(`${this.apiAds}/random/${id}`);
  }

  incrementViews(id: any): Observable<any> {
    return this.httpClient.put(`${this.apiAds}/views/${id}`, {});
  }
}
