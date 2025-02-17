import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideHttpClient } from '@angular/common/http';
import { LoginFormComponent } from './app/login-form/login-form.component';

bootstrapApplication(AppComponent, appConfig).catch((err) =>
  console.error(err)
);

bootstrapApplication(LoginFormComponent, {
  providers: [
    provideHttpClient(),
  ],
}).catch((err) => console.error(err));
