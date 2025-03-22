import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { RegisterModalComponent } from '../register-modal/register-modal.component';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { LocalStorageService } from '../../../services/local-storage.service';
import { Router } from '@angular/router';
import { UserService } from '../../../services/user.service';
import { AuthService } from '../../../services/auth.service';
import Swal from 'sweetalert2';
import { NotLogoDirective } from '../../../directives/not-logo.directive';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatIconModule,
    MatProgressSpinnerModule,
    NavbarComponent,
    NotLogoDirective
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class LoginComponent {
  loginForm: FormGroup;
  registerForm: FormGroup;
  hide = true;
  isModalVisible = false;
  registerModalTitle = '¡Regístrate!';
  logoUrl = '';
  hidePassword = true;
  isLoading = false;
  isLoginMode = false;

  constructor(
    private fb: FormBuilder,
    private _authService: AuthService,
    private _localStorageService: LocalStorageService,
    private _router: Router,
    private _userService: UserService
  ) {
    this.loginForm = this.fb.group({
      usernameOrEmail: ['', Validators.required],
      password: ['', Validators.required],
    });

    this.registerForm = this.fb.group(
      {
        email: ['', [Validators.required, Validators.email]],
        username: ['', Validators.required],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', Validators.required],
      },
      { validators: this.passwordMatchValidator }
    );
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value || '';
    const confirmPassword = form.get('confirmPassword')?.value || '';
    return password === confirmPassword ? null : { passwordMismatch: true };
  }

  openRegisterModal() {
    this.isModalVisible = true;
    document.body.classList.add('is-modal-active');
  }

  closeRegisterModal() {
    this.isModalVisible = false;
    document.body.classList.remove('is-modal-active');
    this.isLoading = false;
  }

  onRegister() {
    if (this.registerForm.valid) {
      const data = {
        email: this.registerForm.get('email')?.value,
        username: this.registerForm.get('username')?.value,
        password: this.registerForm.get('password')?.value,
      };
      const registerData = this.registerForm.value;
      this.isLoading = true;

      setTimeout(() => {
        // this._authService.registerUser(registerData).subscribe(
        //   (response : any) => {
        //     this.isLoading = false;
        //     Swal.fire("¡Registro exitoso!", "Por favor, inicia sesión", "success");
        //     this.registerForm.reset();
        //     this.isModalVisible = false;
        //     document.body.classList.remove("is-modal-active");
        //   }, (error: any) => {
        //     Swal.fire({
        //       title: 'Error!',
        //       text: 'No se pudo registrar al usuario.'+ error.error.message,
        //       icon: 'error'
        //     })
        //     this.registerForm.reset();
        //     this.isLoading = false;
        //     this.registerForm.markAllAsTouched();
        //   }
        // );
      }, 1000);
      this.registerForm.reset();
    } else {
      Swal.fire('Error', 'Por favor, complete los campos requeridos', 'error');
      this.isLoading = false;
      this.registerForm.reset();
      return;
    }
  }

  get usernameOrEmailHasErrorRequired() {
    return this.loginForm.get('usernameOrEmail')?.hasError('required');
  }

  get passwordHasErrorRequired() {
    return this.loginForm.get('password')?.hasError('required');
  }
  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  forgotPassword() {
    this._router.navigate(['/recovery-password']);
  }

  onLogin() {
    this.isLoading = true;

    if (this.loginForm.invalid) {
      Swal.fire('Error', 'Por favor, complete los campos requeridos', 'error');
      return;
    }

    const { usernameOrEmail, password } = this.loginForm.value;

    const data = {
      usernameOrEmail,
      password,
    };

    this._authService.login(data).subscribe({
      next: (response: any) => {
        this._localStorageService.setItem(this._localStorageService.USER_ID, response.id);
        this._localStorageService.setItem(this._localStorageService.USER_NAME, response.username);
        this.setImgProfile();

        if (response.is2FA) {
          this._router.navigate(['/verify-2fa']);
          return;
        }
        this._router.navigate(['/home']);
      },
      error: (error: any) => {
        if (error.error.status === 401) {
          Swal.fire({
            title: 'Error',
            text: 'Usuario no verificado',
            icon: 'error',
            confirmButtonText: 'Reenviar verificación',
          }).then((result) => {
            if (result.isConfirmed) {
              this._authService
                .sendEmailVerification(usernameOrEmail)
                .subscribe({
                  next: (res: any) => {
                    Swal.fire({
                      title: 'Correo de verificación reenviado',
                      text: 'Por favor, verifica tu correo para poder acceder',
                      icon: 'success',
                      confirmButtonText: 'Ok',
                    });
                  },
                  error: (err: any) => {
                    Swal.fire({
                      title: 'Error',
                      text:
                        'No se pudo reenviar el correo de verificación' +
                        err.error.message,
                      icon: 'error',
                      confirmButtonText: 'Ok',
                    });
                  },
                });
            }
          });
        } else {
          Swal.fire({
            title: 'Error!',
            text: error.error.message,
            icon: 'error',
          });
        }
        this.isLoading = false;

        this.loginForm.reset({
          usernameOrEmail: '',
          password: '',
        });

        this.loginForm.markAllAsTouched();
      },
      complete: () => {
        this.isLoading = false;
      },
    });
  }

  setImgProfile() {
    this._userService
      .getUserInfo(this._localStorageService.getItem(this._localStorageService.USER_ID))
      .subscribe({
        next: (response: any) => {
          this._localStorageService.setItem(this._localStorageService.USER_PHOTO, response.path);
        },
        error: (err) => {
          console.error('Error obteniendo la imagen de perfil:', err);
        },
      });
  }
}
