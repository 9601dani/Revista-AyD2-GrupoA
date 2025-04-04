import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { LocalStorageService } from './local-storage.service';
import { Module } from '../models/Module.model';

import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { UserInformation } from '../models/UserInformation.Model';
import { Magazine } from '../models/Magazine.model';
import { Label } from '../models/Label.model';
import { Category } from '../models/Category.mode';
import { AllMagazineResponse } from '../models/AllMagazine.mode';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  readonly apiMagazine = `${environment.API_URL}/v1/magazines`;
  readonly apiSuscriptions = `${environment.API_URL}/v1/suscriptions`;
  readonly apiUser = `${environment.API_URL}/v1/users`;
  readonly apiCategories = `${environment.API_URL}/v1/categories`;
  readonly apiAdmin = `${environment.API_URL}/v1/admin`;

  constructor(
    private http: HttpClient,
    private _localStorage: LocalStorageService
  ) {}

  getPages(id: number): Observable<Module[]> {
    return this.http.get<Module[]>(`${this.apiUser}/pages/${id}`);
  }

  getUserInfo(userId: number): Observable<any> {
    return this.http.get<UserInformation>(`${this.apiUser}/info/${userId}`);
  }

  updateUserInfo(body: UserInformation): Observable<any> {
    return this.http.put<UserInformation>(`${this.apiUser}/info/update`, body);
  }

  updatePhotoPath(formData: FormData, id: number): Observable<any> {

    return this.http.put<any>(`${this.apiUser}/info/update/photo_path/${id}`, formData);
  }

  updateCurrentBalance(body: any): Observable<any> {

    return this.http.put<UserInformation>(`${this.apiUser}/info/update/current_balance`, body);
  }

  /* Labels */

  getAllLabels(): Observable<any>{
    return this.http.get<Label[]>(`${this.apiUser}/labels/all`)
  }

  getlabelsForUser(fkUser: number): Observable<any>{
    return this.http.get<Label[]>(`${this.apiUser}/labels/${fkUser}`)
  }

  saveLabeslToUser(body: any): Observable<any>{
    return this.http.post<any>(`${this.apiUser}/labels/save`, body)
  }

  /* Categories */

  getAllGategories():Observable<any>{
    return this.http.get<Category[]>(`${this.apiCategories}/all`)
  }


  /* Magazines */
  saveMagazine(formData: FormData): Observable<any> {
    return this.http.post<any>(`${this.apiMagazine}/save`, formData);
  }

  getMagazineByIdUser(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiMagazine}/getByUserId/${id}`);
  }

  getMagazineById(id: number): Observable<any> {
    return this.http.get<AllMagazineResponse>(`${this.apiMagazine}/get/${id}`);
  }

  getAllMagazines():Observable<any>{
    return this.http.get<any>(`${this.apiMagazine}/getAll`)
  }

  /** Suscriptions */

  saveSuscription(body: any): Observable<any>{
    return this.http.post<any>(`${this.apiSuscriptions}/save`, body)
  }

  getSuscriptionsForUser(fkUser: number):Observable<any>{
    return this.http.get<any>(`${this.apiSuscriptions}/user/${fkUser}`)
  }

  getSuscriptionsWithMagazineForUser(fkUser: number):Observable<any>{
    return this.http.get<any>(`${this.apiSuscriptions}/all/${fkUser}`)
  }

  getSuscriptionById(id: number):Observable<any>{
    return this.http.get<any>(`${this.apiSuscriptions}/${id}`)
  }

  updateIsLike(body:any):Observable<any>{
    return this.http.put<any>(`${this.apiSuscriptions}/update/like`, body)
  }

  saveComment(body: any): Observable<any>{
    return this.http.post<any>(`${this.apiSuscriptions}/comment/save`, body)
  }

  updateMagazine(formData: FormData): Observable<any>{
    return this.http.put<any>(`${this.apiMagazine}/update`,formData)
  }

  // Reports

  getCommentReport(body: any): Observable<any> {
    return this.http.post<any>(`${this.apiSuscriptions}/comment/report1`, body);
  }

  getSuscriptionReport(body: any): Observable<any> {
    return this.http.post(`${this.apiSuscriptions}/report/report2`, body);
  }

  // ADMIN REPORTS
  getEarningsReport(from: any, to: any): Observable<any> {
    let params = new HttpParams();
    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);
    return this.http.get<any>(`${this.apiAdmin}/earnings`, {
      params
    });
  }

  getPurchasedAds(from: any, to: any): Observable<any> {
    let params = new HttpParams();
    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);
    return this.http.get(`${this.apiAdmin}/ads-purchased`, { params });
  }

  getAdvertiserEarnings(from: any, to: any, userId: any): Observable<any> {
    let params = new HttpParams();
    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);
    if (userId) params = params.set('username', userId);
    return this.http.get(`${this.apiAdmin}/advertiser-earnings`, { params });
  }


  getPopularMagazines(from: any, to: any): Observable<any> {
    let params = new HttpParams();
    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);
    return this.http.get(`${this.apiAdmin}/popular-magazines`, { params });
  }


  getMostCommentedMagazines(from: any, to: any): Observable<any> {
    let params = new HttpParams();
    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);
    return this.http.get(`${this.apiAdmin}/commented-magazines`, { params });
  }

  getEffectiveness(from: any, to: any): Observable<any> {
    let params = new HttpParams();
    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);
    return this.http.get(`${this.apiAdmin}/effectiveness`, { params });
  }

  getTopLikedMagazines(body: any): Observable<any> {
    return this.http.post(`${this.apiSuscriptions}/report/top-liked`, body);
  }

  getPaymentReport(body: any): Observable<any> {
    return this.http.post(`${this.apiSuscriptions}/report/payments`, body);
  }

}
