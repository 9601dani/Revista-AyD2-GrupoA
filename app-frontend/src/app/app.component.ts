
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {AdContainerComponent} from './components/commons/ad-container/ad-container.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AdContainerComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'app-frontend';
}
