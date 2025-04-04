import {Component, TemplateRef, ViewChild} from '@angular/core';
import {NavbarComponent} from "../../commons/navbar/navbar.component";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {UserService} from '../../../services/user.service';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';

@Component({
  selector: 'app-report5',
  imports: [
    NavbarComponent,
    NgIf,
    NgForOf,
    ReactiveFormsModule,
    FormsModule,
    MatButtonModule, MatIconModule,
    MatDialogModule, DatePipe
  ],
  templateUrl: './report5.component.html',
  styleUrl: './report5.component.scss',
  standalone: true,
})
export class Report5Component {
  startDate: any;
  endDate: any;
  magazineId: any;
  report: any[] = [];
  comments : any[] = [];

  @ViewChild('myDialog') myDialog!: TemplateRef<any>;

  constructor(private _userService: UserService,
              private dialog: MatDialog) {
  }

  getReport() {
    this._userService.getMostCommentedMagazines(this.startDate, this.endDate).subscribe({
      next: (res: any) => {
        console.log(res);
        this.report = res;
      },
      error: err => {
        console.error(err);
      }
    })
  }

  openModal(id: any) {
    const value = this.report.find(r => r.id == id);
    this.comments = value.comments;
    console.log(value);
    this.dialog.open(this.myDialog, {
      panelClass: "bancos-cuentas-form-dialog",
      data: {
        // action,
        // codigoEmpleado
      },
      width: "800px",
    });
  }
}
