import { Component } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-payment-report',
  imports: [NavbarComponent, CommonModule, FormsModule],
  templateUrl: './payment-report.component.html',
  styleUrl: './payment-report.component.scss',
})
export class PaymentReportComponent {
  startDate: string = '';
  endDate: string = '';
  magazineId?: number;
  payments: any[] = [];

  constructor(private userService: UserService) {}

  getPaymentReport(): void {
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
      magazineId: this.magazineId || null,
    };

    this.userService.getPaymentReport(body).subscribe({
      next: (data) => {
        this.payments = data;
      },
      error: (err) => {
        console.error(err);
        Swal.fire({
          title: 'Error al obtener los datos',
          icon: 'error',
        });
      },
    });
  }

  getTotalPayments(): number {
    return this.payments.reduce((sum, p) => sum + p.pay, 0);
  }
}
