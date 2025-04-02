import { Component } from '@angular/core';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import { UserService } from '../../../services/user.service';
import { LocalStorageService } from '../../../services/local-storage.service';
import { CommonModule } from '@angular/common';
import { DocumentPipe } from '../../../pipes/document.pipe';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view',
  standalone: true,
  imports: [NavbarComponent, CommonModule, DocumentPipe],
  templateUrl: './view.component.html',
  styleUrl: './view.component.scss'
})
export class ViewComponent{

  magazines: any[] = [];
  constructor( private _userService: UserService, private _localStorage: LocalStorageService, private _router: Router
  ){}

  ngOnInit(){
    this._userService.getMagazineByIdUser(this._localStorage.getItem("user_id")).subscribe({
          next: (response) => {
            console.log(response)
            this.magazines = response;
          }, error: (err) => {
            console.log(err)
          }
    
        }) 
  }

  onEditMagazine(id: number){
    this._router.navigate(['editor/edit-magazine', id])
  }

}
