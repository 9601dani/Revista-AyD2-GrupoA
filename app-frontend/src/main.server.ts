import { provideServerRendering } from '@angular/platform-server';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { config } from './app/app.config.server';
import { serverRoutes } from './app/app.routes.server';

const bootstrap = () =>
  bootstrapApplication(AppComponent, {
    ...config,
    providers: [
      ...config.providers,
      provideServerRendering(),
    ],
  });

bootstrap['serverRoutes'] = serverRoutes;

export default bootstrap;
