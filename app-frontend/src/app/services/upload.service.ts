import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  readonly apiUpload = `${environment.API_URL}/v1/uploads`;
  constructor(private http: HttpClient) { }

  saveDocument(file: File): Observable<any>{
    return this.http.post<any>(`${this.apiUpload}/documents`,file)
  }
}
