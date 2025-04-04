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

    if (!this.startDate || !this.endDate) {
      Swal.fire({
        title: 'Debes ingresar ambas fechas',
        icon: 'error',
      });
      return;
    }

    const body = {
      startDate: this.startDate,
      endDate: this.endDate,
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
