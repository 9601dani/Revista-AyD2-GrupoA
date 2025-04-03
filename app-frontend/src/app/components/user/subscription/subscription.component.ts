import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { LocalStorageService } from '../../../services/local-storage.service';
import { UserService } from '../../../services/user.service';
import { FormsModule } from '@angular/forms';
import { DocumentPipe } from '../../../pipes/document.pipe';
import Swal from 'sweetalert2';
import {
  capitalizeCategories,
  capitalizeLabels,
} from '../../../helpers/helpers';

@Component({
  selector: 'app-subscription',
  imports: [NavbarComponent, CommonModule, FormsModule, DocumentPipe],
  templateUrl: './subscription.component.html',
  styleUrl: './subscription.component.scss',
})
export class SubscriptionComponent implements OnInit {
  subscriptionId: number | undefined;
  subscription: any;
  public capitalizeLabels = capitalizeLabels;
  public capitalizeCategories = capitalizeCategories;
  authorModalOpen = false;
selectedAuthor: { username: string; email: string } | null = null;


  commentText = '';

  constructor(
    private _route: ActivatedRoute,
    private _localStorageService: LocalStorageService,
    private _userSevice: UserService
  ) {}

  ngOnInit(): void {
    this._route.params.subscribe((params) => {
      if (params['id']) {
        this.subscriptionId = +params['id'];
      }
    });

    if (this.subscriptionId) {
      this._userSevice.getSuscriptionById(this.subscriptionId).subscribe({
        next: (value) => {
          console.log(value);
          this.subscription = value;
        },
      });
    }
  }

  getCategoryNames(): string {
    const capitalized = capitalizeCategories(this.subscription.magazine.categories || []);
    return capitalized.map(c => c.name).join(', ');
  }
  
  getLabelNames(): string {
    const capitalized = capitalizeLabels(this.subscription.magazine.labels || []);
    return capitalized.map(l => l.name).join(', ');
  }
  

  toggleLike(sub: any) {
    sub.isLike = !sub.isLike;

    const body = {
      id: sub.id,
      isLike: sub.isLike,
    };

    this._userSevice.updateIsLike(body).subscribe({
      next: (value) => {
        if (sub.isLike) {
          Swal.fire({
            icon: 'success',
            title: 'Te gusta esta revista',
          });
        } else {
          Swal.fire({
            icon: 'success',
            title: 'Ya no te gusta esta revista',
          });
        }
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  sendComment(sub: any) {
    const body = {
      fkSuscription: sub.id,
      fkMagazine: sub.magazine.id,
      content: this.commentText,
    };

    this._userSevice.saveComment(body).subscribe({
      next: (value) => {
        console.log(value);
        this.commentText = '';

        const newComment = {
          ...value,
          dateCreated: new Date().toISOString(),
          subscription: {
            user: sub.user,
          },
        };

        if (!sub.magazine.comments) {
          sub.magazine.comments = [];
        }

        sub.magazine.comments.unshift(newComment);
        sub.magazine.comments = sub.magazine.comments.slice(0, 10);

        Swal.fire({
          icon: 'success',
          title: 'El comentario se ha enviado',
          showConfirmButton: false,
          timer: 1500,
        });
      },
      error: (err) => {
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: 'Error al guardar comentario',
          showConfirmButton: false,
          timer: 1500,
        });
      },
    });
  }

  sortedComments() {
    return [...(this.subscription.magazine.comments || [])]
      .sort(
        (a, b) =>
          new Date(b.dateCreated).getTime() - new Date(a.dateCreated).getTime()
      )
      .slice(0, 10);
  }

  openAuthorModal(author: any) {
    this.selectedAuthor = author;
    this.authorModalOpen = true;
  }
  
  closeAuthorModal() {
    this.authorModalOpen = false;
    this.selectedAuthor = null;
  }
}
