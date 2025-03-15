import { Routes } from '@angular/router';
import { HomeComponent } from './components/commons/home/home.component';
import { NotFoundComponent } from './components/commons/not-found/not-found.component';

export const routes: Routes = [
    {path: '', redirectTo: 'home', pathMatch:'full'},
    {path: 'home', component:   HomeComponent},


    {path: '**', component: NotFoundComponent}
];


export class AppRountingModule {}