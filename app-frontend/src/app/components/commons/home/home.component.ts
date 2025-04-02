import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { LocalStorageService } from '../../../services/local-storage.service';
import { UserService } from '../../../services/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  capitalizeCategories,
  capitalizeLabels,
} from '../../../helpers/helpers';
import { Category } from '../../../models/Category.mode';
import { Label } from '../../../models/Label.model';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MagazineType } from '../../../models/Magazine.model';
import { SourceTextModule } from 'vm';
import { DocumentPipe } from '../../../pipes/document.pipe';
import { MatDialog } from '@angular/material/dialog';
import { PreviewMagazineModalComponent } from '../preview-magazine-modal/preview-magazine-modal.component';

@Component({
  selector: 'app-home',
  imports: [NavbarComponent, CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {


  magazines: any[] = [];
  suscriptions: any[] = [];

  filteredMagazines: any[] = [];

  categories: any[] = [];
  labels: any[] = [];

  selectedCategory: string = '';
  selectedLabel: string = '';

  constructor(
    private _localStorageService: LocalStorageService,
    private _userService: UserService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    const userId = this._localStorageService.getItem(
      this._localStorageService.USER_ID
    );
    if (userId) {
      this._userService.getSuscriptionsForUser(userId).subscribe({
        next: (value) => {
          this.suscriptions = value;
        },
      });
    }

    this._userService.getAllMagazines().subscribe({
      next: (response) => {
        this.magazines = response;
        this.filteredMagazines = response;
        console.log(this.magazines);

        this.categories = capitalizeCategories(
          Array.from(
            new Map<number, Category>(
              response
                .flatMap((magazine: any) => magazine.categories || [])
                .map((category: any) => [category.id, category])
            ).values()
          )
        );

        this.labels = capitalizeLabels(
          Array.from(
            new Map<number, Label>(
              response
                .flatMap((magazine: any) => magazine.labels || [])
                .map((label: any) => [label.id, label])
            ).values()
          )
        );
      },
      error: (err) => console.log(err),
    });
  }

  applyFilters() {
    this.filteredMagazines = this.magazines.filter((magazine: any) => {
      const matchesCategory = this.selectedCategory
        ? magazine.categories?.some(
            (cat: any) =>
              cat.name.toLowerCase() === this.selectedCategory.toLowerCase()
          )
        : true;

      const matchesLabel = this.selectedLabel
        ? magazine.labels?.some((label: any) =>
            label.name.toLowerCase().includes(this.selectedLabel.toLowerCase())
          )
        : true;

      return matchesCategory && matchesLabel;
    });
  }

  clearFilters() {
    this.selectedCategory = '';
    this.selectedLabel = '';
    this.filteredMagazines = this.magazines;
  }

  disableButton(magazine: any): boolean {

    const alreadySubscribed = this.suscriptions.some(s => s.fkMagazine == magazine.id);
    return !magazine.canSubscribe || alreadySubscribed;
  }

  changeButtonName(magazine: any): string{
    
    const alreadySubscribed = this.suscriptions.some(s => s.fkMagazine == magazine.id);

    return alreadySubscribed ? 'Subscrito' : 'Subscribirse'
  }
  

  onSubscribeMagazine(magazine: any) {
    const userId = this._localStorageService.getItem(
      this._localStorageService.USER_ID
    );
    if (!userId) {
      this.router.navigate(['/login']);
    }

    Swal.fire({
      title: '¿Quieres suscribirte a esta revista?',
      text: 'Suscripción a la revista ' + magazine.name,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Suscribirse',
    }).then((result) => {
      if (result.isConfirmed) {
        const body = {
          fkUser: userId,
          fkMagazine: magazine.id,
          pay: magazine.price,
        };

        this._userService.saveSuscription(body).subscribe({
          next: (suscriptionResponse: any) => {
            if (magazine.type == MagazineType.PAID) {
              const body = {
                fkUser: userId,
                sum: false,
                current_balance: magazine.price,
              };

              this._userService.updateCurrentBalance(body).subscribe({
                next: (value) => {
                  Swal.fire({
                    title: 'Suscripción Realizada',
                    text:
                      'Te haz suscrito a esta revista. Proximo pago: ' +
                      suscriptionResponse.dateEnded,
                    icon: 'success',
                  });
                },
                error: (err) => {
                  console.log(err);
                },
              });
            } else {
              Swal.fire({
                title: 'Suscripción Realizada',
                text: 'Te haz suscrito a esta revista',
                icon: 'success',
              });
            }
          },
          error: (err) => {
            console.log(err);
          },
        });
      }
    });
  }

  viewPath(magazinePath: string, magazine: any) {
    this.dialog.open(PreviewMagazineModalComponent, {
      data: magazine,
      width: '80vw',
      maxHeight: '90vh'
    });
  }
}
