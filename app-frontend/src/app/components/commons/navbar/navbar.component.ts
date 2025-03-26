import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { LocalStorageService } from '../../../services/local-storage.service';
import { UserService } from '../../../services/user.service';
import Swal from 'sweetalert2';
import { NotLogoDirective } from '../../../directives/not-logo.directive';
import { UserInformation } from '../../../models/UserInformation.Model';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterLink, NotLogoDirective],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent implements OnInit {
  isLogged: boolean = false;
  isActive: boolean = false;
  showButtons: boolean = true;
  activeModule: any;
  modules: any[] = [];
  logoUrl = '';
  userPhoto = '';
  username = '';
  notOptionsUrls = [
    'login',
    'verify-2fa',
    'verify-email',
    'recovery-password',
    'reset-password',
  ];

  constructor(
    private _localStorageService: LocalStorageService,
    private router: Router,
    private _userService: UserService
  ) {}

  ngOnInit(): void {
    this.isLogged =
      this._localStorageService.getItem(this._localStorageService.USER_ID) !==
      null;
    if (this.isLogged) {
      this.username = this._localStorageService.getItem(
        this._localStorageService.USER_NAME
      );
      this.showButtons = true;
      this.getPages();
      this.setUserPhoto();
    }

    this.showOptions();
  }

  getPages() {
    const id = this._localStorageService.getItem(
      this._localStorageService.USER_ID
    );
    this._userService.getPages(id).subscribe({
      next: (response: any) => {
        this.modules = response;
      },
    });
  }

  setUserPhoto() {
    const id = this._localStorageService.getItem(
      this._localStorageService.USER_ID
    );
    this.userPhoto = this._localStorageService.getItem(
      this._localStorageService.USER_PHOTO
    );

    if (!this.userPhoto) {
      this._userService.getUserInfo(id).subscribe({
        next: (response: UserInformation) => {
          this.userPhoto = response.photo_path;
          this._localStorageService.setItem(
            this._localStorageService.USER_PHOTO,
            response.photo_path
          );
        },
        error: (error) => {
          Swal.fire({
            title: 'Error!',
            text: error.error.message,
            icon: 'error',
          });
        },
      });
    }
  }

  showOptions() {
    const url = this.router.url.split('/')[1];
    this.showButtons = !this.notOptionsUrls.includes(url);
  }

  toggleNavbar() {
    this.isActive = !this.isActive;
  }

  toggleSubmenu(module: string) {
    this.activeModule = this.activeModule === module ? null : module;
  }

  myAccount() {
    this.router.navigate(['/edit/profile']);
  }

  logout() {
    this._localStorageService.logout();
    this.router.navigate(['/login']);
  }
}
