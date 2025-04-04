import { ServerRoute, RenderMode } from '@angular/ssr';
import { inject } from '@angular/core';
import { UserService } from './services/user.service';

export const serverRoutes: ServerRoute[] = [
  { path: '', renderMode: RenderMode.Prerender },
  { path: 'home', renderMode: RenderMode.Prerender },
  { path: 'login', renderMode: RenderMode.Prerender },
  { path: 'magazines', renderMode: RenderMode.Prerender },
  { path: 'magazines/search', renderMode: RenderMode.Prerender },

  { path: 'editor', renderMode: RenderMode.Prerender },
  { path: 'editor/new-magazine', renderMode: RenderMode.Prerender },
  { path: 'editor/my-magazines', renderMode: RenderMode.Prerender },

  { path: 'reports', renderMode: RenderMode.Prerender },
  { path: 'reports/report7', renderMode: RenderMode.Prerender },
  { path: 'reports/report8', renderMode: RenderMode.Prerender },
  { path: 'reports/report9', renderMode: RenderMode.Prerender },
  { path: 'reports/report10', renderMode: RenderMode.Prerender },

  {
    path: 'editor/edit-magazine/:id', renderMode: RenderMode.Client
  },  
  { path: 'user', renderMode: RenderMode.Prerender },
  { path: 'user/profile', renderMode: RenderMode.Prerender },
  { path: 'user/buy-ad', renderMode: RenderMode.Prerender },
  { path: 'user/my-ads', renderMode: RenderMode.Prerender },
  { path: 'user/my-subscriptions', renderMode: RenderMode.Prerender },
  { path: 'user/subscription/:id', renderMode: RenderMode.Client },
  { path: 'admin', renderMode: RenderMode.Prerender },
  { path: 'admin/settings', renderMode: RenderMode.Prerender },

];
