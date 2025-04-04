import { Component } from '@angular/core';
import {NavbarComponent} from "../../commons/navbar/navbar.component";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-report2',
  imports: [
    NavbarComponent,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule,
    DatePipe
  ],
  templateUrl: './report2.component.html',
  styleUrl: './report2.component.scss',
  standalone: true

})
export class Report2Component {

  startDate: any;
  endDate: any;
  magazineId: any;
  report: any[] = [];

  constructor(private _userService: UserService) {
  }

  getReport() {
    this._userService.getPurchasedAds(this.startDate, this.endDate).subscribe({
      next: res => {
        console.log(res);
        this.report = res;
      },
      error: err => {
        console.error(err);
      }
    })

  }
}
