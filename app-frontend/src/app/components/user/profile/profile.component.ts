import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { LocalStorageService } from '../../../services/local-storage.service';
import Swal from 'sweetalert2';
import { UserInformation } from '../../../models/UserInformation.Model';
import { ImagePipe } from '../../../pipes/image.pipe';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile',
  imports: [NavbarComponent, ReactiveFormsModule, ImagePipe, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;
  user_id!: number;
  username: string = '';

  userProfileAll: UserInformation = {
    id: 0,
    name: '',
    age: 0,
    fkUser: 0,
    photo_path: '',
    description: '',
    current_balance: 0,
  };

  initialValues: any;

  selectedFile: File | null = null;
  selectedPreviewImage: string | null = null;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private _userService: UserService,
    private _localStorageService: LocalStorageService
  ) {}

  ngOnInit(): void {
    this.user_id = this._localStorageService.getItem(
      this._localStorageService.USER_ID
    );
    this.username = this._localStorageService.getItem(
      this._localStorageService.USER_NAME
    );

    this.getUserProfile();
    this.profileForm = this.fb.group({
      name: ['', Validators.required],
      age: ['', [Validators.required, Validators.min(0)]],
      description: ['', Validators.required],
    });
  }

  getUserProfile(): void {
    if (this.user_id === null) {
      Swal.fire({
        title: 'Error',
        text: 'No se ha podido obtener el usuario',
        icon: 'error',
      });
      return;
    } else {
      this._userService.getUserInfo(this.user_id).subscribe({
        next: (res: UserInformation) => {
          this.userProfileAll = res;
          this.initialValues = this.profileForm.value;
          this.profileForm.patchValue({
            name: this.userProfileAll.name,
            age: this.userProfileAll.age,
            description: this.userProfileAll.description,
          });
        },
        error: (err) => {
          Swal.fire({
            title: 'Error',
            text: 'No se ha podido obtener el usuario: ' + this.user_id,
            icon: 'error',
          });
        },
      });
    }
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    const allowedTypes = ['image/jpeg', 'image/png', 'image/jpg'];

    if (file && allowedTypes.includes(file.type)) {
      this.selectedFile = file;
      this.errorMessage = '';

      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.selectedPreviewImage = e.target.result;
      };
      reader.readAsDataURL(file);
    } else {
      this.selectedFile = null;
      this.selectedPreviewImage = null;
      this.errorMessage =
        'Solo se permiten imágenes con formato .jpg, .jpeg o .png';
    }
  }

  onUpload(): void {
    if (this.selectedFile) {
      console.log(this.selectedFile);
      const formData = new FormData();
      formData.append('file', this.selectedFile);

      this._userService.updatePhotoPath(formData, this.user_id).subscribe({
        next: (res: any) => {
          Swal.fire({
            icon: 'success',
            title: '¡Foto de Perfil guardada!',
            text: 'La foto de perfil se ha guardado correctamente.',
            confirmButtonText: 'Aceptar'
          })

          console.log(res);
          const imagePath = res.message;
          this.userProfileAll.photo_path = imagePath;
          this.selectedFile = null;
          this.selectedPreviewImage = null;
        },
        error: (err) => {
          Swal.fire({
            title: 'Error',
            text: 'No se ha podido actualizar la imagen de perfil',
            icon: 'error',
          });
          console.log(err);
        },
      });
    }
  }

  saveChanges(): void {
    if (this.profileForm.valid) {
      const profileData: UserInformation = this.profileForm.value;
      if (profileData.name == null || profileData.name == '') {
        Swal.fire({
          title: 'Error',
          text: 'El campo Nombre no puede estar vacío',
          icon: 'error',
        });
        return;
      }
      if (profileData.description == null || profileData.description == '') {
        Swal.fire({
          title: 'Error',
          text: 'El campo descripción no puede estar vacío',
          icon: 'error',
        });
        return;
      }
      if (profileData.age == null || profileData.age == 0) {
        Swal.fire({
          title: 'Error',
          text: 'El campo Edad no puede estar vacío',
          icon: 'error',
        });
        return;
      }

      profileData.fkUser = this.user_id;
      profileData.photo_path = this.userProfileAll.photo_path;

      this._userService.updateUserInfo(profileData).subscribe(
        (res: any) => {
          this.userProfileAll = res;
          this.initialValues = this.profileForm.value;
          Swal.fire({
            title: 'Cambios guardados',
            text: 'Los cambios se han guardado correctamente',
            icon: 'success',
          });
        },
        (err) => {
          Swal.fire({
            title: 'Error',
            text: 'No se ha podido guardar los cambios',
            icon: 'error',
          });
        }
      );
    } else {
      Swal.fire({
        title: 'Error',
        text: 'Por favor, complete los campos vacios',
        icon: 'error',
      });
    }
  }
}
