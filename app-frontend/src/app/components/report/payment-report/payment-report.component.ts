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
    const start = this.startDate ? `${this.startDate}T00:00:00` : '2000-01-01T00:00:00';
    const end = this.endDate ? `${this.endDate}T23:59:59` : '3000-12-31T23:59:59';

    const body = {
      startDate: start,
      endDate: end,
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
