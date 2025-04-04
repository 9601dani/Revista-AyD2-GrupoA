import { Component } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LocalStorageService } from '../../../services/local-storage.service';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-suscription-report',
  imports: [FormsModule, CommonModule, NavbarComponent],
  templateUrl: './suscription-report.component.html',
  styleUrl: './suscription-report.component.scss',
})
export class SuscriptionReportComponent {
  startDate: string = '';
  endDate: string = '';
  magazineId?: number;
  authorId: number = 1;
  report: any[] = [];

  constructor(
    private userService: UserService,
    private _localStorageService: LocalStorageService
  ) {}

  getSuscriptionReport(): void {
    const userId = this._localStorageService.getItem(
      this._localStorageService.USER_ID
    );

    const start = this.startDate ? `${this.startDate}T00:00:00` : '2000-01-01T00:00:00';
    const end = this.endDate ? `${this.endDate}T23:59:59` : '3000-12-31T23:59:59';
    

    const body = {
      startDate: start,
      endDate: end,
      authorId: userId,
      magazineId: this.magazineId || null,
    };

    this.userService.getSuscriptionReport(body).subscribe({
      next: (data: any) => {
        this.report = data;
      },
      error: (err: any) => {
        console.error(err);
        Swal.fire({
          title: 'Error en el reporte',
          icon: 'error',
        });
      },
    });
  }
}
