import {Component, OnDestroy, OnInit} from '@angular/core';
import {AdsService} from '../../../services/ads/ads.service';
import {ActivatedRoute, Router} from '@angular/router';
import {LocalStorageService} from '../../../services/local-storage.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-ad-container',
  imports: [],
  templateUrl: './ad-container.component.html',
  styleUrl: './ad-container.component.scss'
})
export class AdContainerComponent implements OnInit {

  latestData: any;

  constructor(
    private _adsService: AdsService,
    private _localStorageService: LocalStorageService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    console.log(this.router.url);
    // this.getRandomAd();

    /*setInterval(() => {
      this.getRandomAd();
      }, 6000)*/
  }

  getRandomAd() {
    const userId = this._localStorageService.getItem(this._localStorageService.USER_ID) || 0;
    this._adsService.getRandomAd(userId).subscribe({
      next: response => {
        console.log(response);
        this.latestData = response;
      },
      error: err => {
        console.error(err);
      }
    })
  }

}
