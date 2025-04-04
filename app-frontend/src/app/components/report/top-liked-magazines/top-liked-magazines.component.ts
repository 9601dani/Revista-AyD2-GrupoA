import { Component } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-top-liked-magazines',
  imports: [FormsModule, CommonModule, NavbarComponent],
  templateUrl: './top-liked-magazines.component.html',
  styleUrl: './top-liked-magazines.component.scss',
})
export class TopLikedMagazinesComponent {
  startDate: string = '';
  endDate: string = '';
  topLiked: any[] = [];

  constructor(private userService: UserService) {}

  getTopLiked(): void {
    const start = this.startDate ? `${this.startDate}T00:00:00` : '2000-01-01T00:00:00';
    const end = this.endDate ? `${this.endDate}T23:59:59` : '3000-12-31T23:59:59';

    const body = {
      startDate: start,
      endDate: end,
    };

    this.userService.getTopLikedMagazines(body).subscribe({
      next: (data) => {
        this.topLiked = data;
      },
      error: (err) => {
        console.error(err);
        Swal.fire({
          title: 'Error en el reporte',
          icon: 'error',
        });
      },
    });
  }
}
