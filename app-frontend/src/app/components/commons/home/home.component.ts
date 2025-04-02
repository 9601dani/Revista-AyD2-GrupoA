import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { LocalStorageService } from '../../../services/local-storage.service';
import { UserService } from '../../../services/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { capitalizeCategories, capitalizeLabels } from '../../../helpers/helpers';
import { Category } from '../../../models/Category.mode';
import { Label } from '../../../models/Label.model';
import { DocumentPipe } from '../../../pipes/document.pipe';

@Component({
  selector: 'app-home',
  imports: [NavbarComponent, CommonModule, FormsModule, DocumentPipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {


  magazines: any[] = [];

  filteredMagazines: any[] = [];

  categories: any[] = [];
  labels: any[] = [];

  selectedCategory: string = '';
  selectedLabel: string = '';


  constructor(private _localStorageService: LocalStorageService, private _userService: UserService){}

  ngOnInit(){
   this._userService.getAllMagazines().subscribe({
      next: (response) => {
        this.magazines = response;
        console.log(response)
        this.filteredMagazines = response;

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
      error: (err) => console.log(err)
    });
  }


  applyFilters() {
    this.filteredMagazines = this.magazines.filter((magazine: any) => {
      const matchesCategory = this.selectedCategory
        ? magazine.categories?.some((cat: any) =>
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
  

  onSubscribeMagazine(id:number){
    console.log("Voy a suscribrime a la revis Id: ", id )

  }

  viewPath(path:string){
    console.log("Path: ", path)

  }
}
