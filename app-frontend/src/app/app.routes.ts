import { Routes } from '@angular/router';
import { HomeComponent } from './components/commons/home/home.component';
import { LoginComponent } from './components/commons/login/login.component';
import { AddMagazineComponent } from './components/magazines/add-magazine/add-magazine.component';

export const routes: Routes = [
    {path: '', redirectTo: 'home', pathMatch:'full'},
    {path: 'home', component:   HomeComponent},
    {path: 'login', component:   LoginComponent},
    {path: 'magazines', children:[
        {path: 'add-magazine', component: AddMagazineComponent }
    ]}

];