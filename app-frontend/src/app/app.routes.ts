import { Routes } from '@angular/router';
import { HomeComponent } from './components/commons/home/home.component';
import { LoginComponent } from './components/commons/login/login.component';
import { AddMagazineComponent } from './components/magazines/add-magazine/add-magazine.component';
import { ViewComponent } from './components/magazines/view/view.component';
import { ProfileComponent } from './components/user/profile/profile.component';
import { authGuard } from './guards/auth.guard';
import {BuyAdComponent} from './components/ads/buy-ad/buy-ad.component';
import {MyAdComponent} from './components/ads/my-ad/my-ad.component';
import { EditMagazineComponent } from './components/magazines/edit-magazine/edit-magazine.component';
import { MySubscriptionsComponent } from './components/user/my-subscriptions/my-subscriptions.component';
import { SubscriptionComponent } from './components/user/subscription/subscription.component';
import { CommentReportComponent } from './components/report/comment-report/comment-report.component';
import { SuscriptionReportComponent } from './components/report/suscription-report/suscription-report.component';
import { SettingsComponent } from './components/admin/settings/settings.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'magazines', children: [
    {path: 'search', component:HomeComponent}
    ],
    canActivate : [authGuard] 
  },
  {
    path: 'editor',
    children: [
      { path: 'new-magazine', component: AddMagazineComponent },
      { path: 'my-magazines', component: ViewComponent },
      { path: 'edit-magazine/:id', component: EditMagazineComponent},
    ],
    canActivate: [authGuard]
  },
  {
    path: 'reports',
    children: [
      { path: 'report7', component: CommentReportComponent},
      { path: 'report8', component: SuscriptionReportComponent}
    ],
    canActivate: [authGuard]
  },
  {
    path: 'user',
    children: [
      { path: 'profile', component: ProfileComponent },
      { path: 'buy-ad', component: BuyAdComponent },
      { path: 'my-ads', component: MyAdComponent },
      { path: 'my-subscriptions', component: MySubscriptionsComponent },
      { path: 'subscription/:id', component: SubscriptionComponent },
    ],
    canActivate: [authGuard]
  },
  {
    path: 'admin',
    children: [
      { path: 'settings', component: SettingsComponent },
    ],
    canActivate: [authGuard]
  }
];
