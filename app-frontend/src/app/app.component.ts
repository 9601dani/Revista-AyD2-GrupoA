
import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {AdContainerComponent} from './components/commons/ad-container/ad-container.component';
import {filter} from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AdContainerComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'app-frontend';

  showAds : boolean = false;
  hiddenRoutes = ['/login'];

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.handleRoutes();
  }

  handleRoutes() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      this.showAds = !this.hiddenRoutes.includes(event.urlAfterRedirects);
    })
  }
}
