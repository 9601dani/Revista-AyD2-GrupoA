import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { LocalStorageService } from '../../../services/local-storage.service';

@Component({
  selector: 'app-home',
  imports: [NavbarComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(private localStorageService: LocalStorageService){

  }
}
