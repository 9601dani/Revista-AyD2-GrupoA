import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { LocalStorageService } from '../../../services/local-storage.service';
import { UserService } from '../../../services/user.service';
import { FormsModule } from '@angular/forms';
import { DocumentPipe } from '../../../pipes/document.pipe';

@Component({
  selector: 'app-subscription',
  imports: [NavbarComponent, CommonModule, FormsModule, DocumentPipe],
  templateUrl: './subscription.component.html',
  styleUrl: './subscription.component.scss',
})
export class SubscriptionComponent implements OnInit {
  subscriptionId: number | undefined;
  subscription: any;

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

  toggleLike(sub: any) {
    sub.isLike = !sub.isLike;
  }
  
  sendComment(sub: any) {
    console.log('Comentario enviado:', this.commentText);
    this.commentText = '';
  }
}
