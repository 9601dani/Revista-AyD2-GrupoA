import { ServerRoute, RenderMode } from '@angular/ssr';
import { inject } from '@angular/core';
import { UserService } from './services/user.service';

export const serverRoutes: ServerRoute[] = [
  { path: '', renderMode: RenderMode.Prerender },
  { path: 'home', renderMode: RenderMode.Prerender },
  { path: 'login', renderMode: RenderMode.Prerender },
  { path: 'magazines', renderMode: RenderMode.Prerender },

  { path: 'editor', renderMode: RenderMode.Prerender },
  { path: 'editor/new-magazine', renderMode: RenderMode.Prerender },
  { path: 'editor/my-magazines', renderMode: RenderMode.Prerender },

  {
    path: 'editor/edit-magazine/:id',
    renderMode: RenderMode.Client,
  }
  ,  
  { path: 'user', renderMode: RenderMode.Prerender },
  { path: 'user/profile', renderMode: RenderMode.Prerender },
  { path: 'user/buy-ad', renderMode: RenderMode.Prerender },
  { path: 'user/my-ads', renderMode: RenderMode.Prerender }
];
