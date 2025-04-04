import {Component, OnDestroy, OnInit} from '@angular/core';
import {AdsService} from '../../../services/ads/ads.service';
import {ActivatedRoute, Router} from '@angular/router';
import {LocalStorageService} from '../../../services/local-storage.service';
import {Subscription} from 'rxjs';
import {AdTemplateComponent} from '../ad-template/ad-template.component';

@Component({
  selector: 'app-ad-container',
  imports: [
    AdTemplateComponent
  ],
  templateUrl: './ad-container.component.html',
  styleUrl: './ad-container.component.scss'
})
export class AdContainerComponent implements OnInit {

  latestData: any = null;

  constructor(
    private _adsService: AdsService,
    private _localStorageService: LocalStorageService,
  ) {
  }

  ngOnInit(): void {
    this.getRandomAd();
  }

  getRandomAd() {
    this.latestData = null;
    const userId = this._localStorageService.getItem(this._localStorageService.USER_ID) || 0;
    this._adsService.getRandomAd(userId).subscribe({
      next: response => {
        this.latestData = {
          type: response.adType.name,
          ...response
        };
        console.log(this.latestData);
      },
      error: err => {
        console.error(err);
      }
    })
  }

  async handleAdClosed() {
    await this.incrementViews();
    // TODO: WAIT 5 SECONS AND GET NEW ADD
    await this.delay(20000);
    this.getRandomAd();
  }

  async incrementViews() : Promise<void> {
    const id = this.latestData.id;
    this.latestData = null;
    return new Promise((resolve) => {
      this._adsService.incrementViews(id).subscribe({
        next: res => {
          console.log(res);
        },
        error: err => {
          console.error(err);
        },
        complete: () => {
          resolve();
        }
      })
    })
  }

  private delay(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

}
