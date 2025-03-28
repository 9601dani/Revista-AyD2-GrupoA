import { Routes } from '@angular/router';
import { HomeComponent } from './components/commons/home/home.component';
import { LoginComponent } from './components/commons/login/login.component';
import { AddMagazineComponent } from './components/magazines/add-magazine/add-magazine.component';
import { ViewComponent } from './components/magazines/view/view.component';
import { ProfileComponent } from './components/user/profile/profile.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'magazines', children: [] },
  {
    path: 'editor',
    children: [
      { path: 'new-magazine', component: AddMagazineComponent },
      { path: 'my-magazines', component: ViewComponent },
    ],
  },

  {
    path: 'user',
    children: [
        {path: 'profile', component: ProfileComponent}
    ]
  }
];
