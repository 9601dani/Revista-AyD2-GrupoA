import { Component } from '@angular/core';
import {NavbarComponent} from "../../commons/navbar/navbar.component";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-report4',
  imports: [
    NavbarComponent,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './report4.component.html',
  styleUrl: './report4.component.scss'
})
export class Report4Component {
  startDate: any;
  endDate: any;
  magazineId: any;
  report: any[] = [];

  constructor(private _userService: UserService) {
  }

  getReport() {
    this._userService.getPopularMagazines(this.startDate, this.endDate).subscribe({
      next: res => {
        console.log(res);
      },
      error: err => {
        console.error(err);
      }
    })
  }
}
