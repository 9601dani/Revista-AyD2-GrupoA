import { Component } from '@angular/core';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import { AdsService } from '../../../services/ads/ads.service';
import { Period } from '../../../models/Period.mode';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-settings',
  standalone:true,
  imports: [NavbarComponent, FormsModule, CommonModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export class SettingsComponent {
  
  periods!:Period[]

  constructor(private _adsService:AdsService){}

  ngOnInit(){
    this._adsService.getAllPeriods().subscribe({
      next: (response:any) => {
        this.periods = response
        console.log(this.periods)
      }
    })
  }
  

  onSubmit() {
    const invalido = this.periods.some(p => p.cost <= 0 || isNaN(p.cost));
    if (invalido) {
      Swal.fire({
        icon: 'warning',
        title: 'Costo inválido',
        text: 'Todos los costos deben ser mayores a cero.'
      });
      return;
    }
  
  this._adsService.saveAllPeriods(this.periods).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Éxito',
          text: 'Costos actualizados correctamente.'
        });
      },
      error: (err) => {
        console.error('Error al guardar los costos:', err);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Hubo un error al guardar los costos. Intenta nuevamente.'
        });
      }
    });
  }
  

}
