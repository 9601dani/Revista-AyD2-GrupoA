import { CommonModule, DatePipe } from '@angular/common';
import { HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { NavbarComponent } from "../../commons/navbar/navbar.component";

interface CommentReport {
  commentId: number;
  content: string;
  dateCreated: string;
  magazineId: number;
  magazineName: string;
  magazineDescription: string;
}

@Component({
  selector: 'app-comment-report',
  imports: [FormsModule, CommonModule, DatePipe, NavbarComponent],
  templateUrl: './comment-report.component.html',
  styleUrl: './comment-report.component.scss',
})
export class CommentReportComponent {
  comments: CommentReport[] = [];
  startDate: string = '';
  endDate: string = '';
  magazineId: number | null = null;

  constructor(private userService: UserService) {}

  getReport(): void {
    if (!this.startDate || !this.endDate) {
      alert('Debes ingresar las fechas de inicio y fin.');
      return;
    }
  
    const body = {
      start: `${this.startDate}T00:00:00`,
      end: `${this.endDate}T23:59:59`,
      magazineId: this.magazineId || null
    };
  
    this.userService.getCommentReport(body).subscribe({
      next: (value: CommentReport[]) => {
        this.comments = value;
      },
      error: (err) => {
        console.log(err);
        alert('Error al obtener los datos');
      }
    });
  }
}
