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
