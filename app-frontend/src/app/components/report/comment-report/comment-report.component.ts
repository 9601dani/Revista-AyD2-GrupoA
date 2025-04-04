import { CommonModule, DatePipe } from '@angular/common';
import { HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { NavbarComponent } from "../../commons/navbar/navbar.component";
import Swal from 'sweetalert2';

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
    const start = this.startDate ? `${this.startDate}T00:00:00` : '2000-01-01T00:00:00';
    const end = this.endDate ? `${this.endDate}T23:59:59` : '3000-12-31T23:59:59';
    
  
    const body = {
      start: start,
      end: end,
      magazineId: this.magazineId || null
    };
  
    this.userService.getCommentReport(body).subscribe({
      next: (value: CommentReport[]) => {
        this.comments = value;
        
      },
      error: (err) => {
        console.log(err);
        Swal.fire({
          title: "Error al obtener los datos",
          icon: "error",
        });
      }
    });
  }
}
