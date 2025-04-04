import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { LocalStorageService } from '../../../services/local-storage.service';
import { UserService } from '../../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-subscriptions',
  imports: [NavbarComponent, CommonModule],
  templateUrl: './my-subscriptions.component.html',
  styleUrl: './my-subscriptions.component.scss',
})
export class MySubscriptionsComponent implements OnInit {

  suscriptions: any[] = [];
  magazines: any[] = [];

  constructor(
    private _localStorageService: LocalStorageService,
    private _userService: UserService,
    private _router:Router
  ) {}

  ngOnInit(): void {
    const userId = this._localStorageService.getItem(
      this._localStorageService.USER_ID
    );
    if (userId) {
      this._userService.getSuscriptionsWithMagazineForUser(userId).subscribe({
        next: (value) => {
          console.log(value);
          this.suscriptions = value;
          this.magazines = value.map((s: any) => s.magazine);
        },
      });
    }
  }

  viewSubscription(id: number){
    this._router.navigate(['user/subscription', id])
  }
}
