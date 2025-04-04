import { Component } from '@angular/core';
import {NavbarComponent} from "../../commons/navbar/navbar.component";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-report3',
  imports: [
    NavbarComponent,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule,
    DatePipe
  ],
  templateUrl: './report3.component.html',
  styleUrl: './report3.component.scss',
  standalone: true

})
export class Report3Component {

  startDate: any;
  endDate: any;
  magazineId: any;
  report: any[] = [];

  constructor(private _userService: UserService) {
  }

  getReport() {
    this._userService.getAdvertiserEarnings(this.startDate, this.endDate, this.magazineId).subscribe({
      next: (res: any) => {
        console.log(res);
        this.report = res;
      },
      error: err => {
        console.error(err);
      }
    })
  }
}
