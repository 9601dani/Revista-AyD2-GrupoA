import {Component, TemplateRef, ViewChild} from '@angular/core';
import {NavbarComponent} from "../../commons/navbar/navbar.component";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UserService} from '../../../services/user.service';
import {MatDialog, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";

@Component({
  selector: 'app-report4',
  imports: [
    NavbarComponent,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule,
    MatIconModule,
    MatButtonModule,
    DatePipe,
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle
  ],
  templateUrl: './report4.component.html',
  styleUrl: './report4.component.scss',
  standalone: true

})
export class Report4Component {
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
    this._userService.getPopularMagazines(this.startDate, this.endDate).subscribe({
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
    this.comments = value.subscriptions;
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
