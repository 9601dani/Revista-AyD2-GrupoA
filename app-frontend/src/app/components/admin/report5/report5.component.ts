import { Component } from '@angular/core';
import {NavbarComponent} from "../../commons/navbar/navbar.component";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UserService} from '../../../services/user.service';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-report5',
  imports: [
    NavbarComponent,
    NgIf,
    ReactiveFormsModule,
    FormsModule,
    MatIconModule,
  ],
  templateUrl: './report5.component.html',
  styleUrl: './report5.component.scss'
})
export class Report5Component {
  startDate: any;
  endDate: any;
  magazineId: any;
  report: any[] = [];

  dataSource = new MatTableDataSource();
  expandedElement: any | null = null;

  columnsToDisplay = ['magazineName'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  innerColumns = ['commenter', 'comment', 'dateCreated'];

  constructor(private _userService: UserService) {
  }

  getReport() {
    this._userService.getMostCommentedMagazines(this.startDate, this.endDate).subscribe({
      next: (res: any) => {
        console.log(res);
        this.report = res;
        this.dataSource = new MatTableDataSource(this.report);
      },
      error: err => {
        console.error(err);
      }
    })
  }

  toggle(element: any) {
    this.expandedElement = this.isExpanded(element) ? null : element;
  }

  isExpanded(element: any): boolean {
    return this.expandedElement === element;
  }

  getCommentDataSource(element: any): MatTableDataSource<any> {
    return new MatTableDataSource(element.comments);
  }
}
