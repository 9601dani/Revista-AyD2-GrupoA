import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import { UserService } from '../../../services/user.service';
import { Magazine } from '../../../models/Magazine.model';
import { UploadService } from '../../../services/upload.service';
import { LocalStorageService } from '../../../services/local-storage.service';

@Component({
  selector: 'app-add-magazine',
  imports: [CommonModule, ReactiveFormsModule, NavbarComponent ],
  templateUrl: './add-magazine.component.html',
  styleUrl: './add-magazine.component.scss'
})
export class AddMagazineComponent {

  magazineForm!: FormGroup
  magazineToSave!: Magazine

  types = [
    { label: 'Gratis', value: 'FREE' },
    { label: 'De Pago', value: 'PAID' }
  ];
  

  constructor( private fb: FormBuilder, private userService: UserService,
    private uploadService:UploadService, private localStorage:LocalStorageService
  ){}

  ngOnInit(): void {
    this.magazineForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      canComment: [false],
      canLike: [false],
      canSubscribe: [false],
      type: ['', Validators.required],
      price: [null, [Validators.required, Validators.min(0)]],
      archivo: [null, Validators.required],
    });

    this.magazineForm.get('type')?.valueChanges.subscribe((value) => {
      const precioControl = this.magazineForm.get('price');
      if (value === 'FREE') {
        precioControl?.setValue(0);
        precioControl?.disable();
      } else {
        precioControl?.enable();
      }
    });
  }
  

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file && file.type === 'application/pdf') {
      this.magazineForm.patchValue({ archivo: file });
      this.magazineForm.get('archivo')?.updateValueAndValidity();
    } else {
      this.magazineForm.patchValue({ archivo: null });
      this.magazineForm.get('archivo')?.setErrors({ invalidType: true });
    }
  }

  onSubmit(): void {
    if (this.magazineForm.valid) {

      console.log(this.magazineForm.value)
      let path_saved:string=''
      
      const file: File | null = this.magazineForm.get('archivo')?.value;

      if (file) {
        /* this.uploadService.saveDocument(file).subscribe({
          next: (response) => {
            console.log('Documento subido correctamente', response);
            
          },
          error: (err) => {
            console.error('Error al subir el documento', err);
          }
        }); */

        /* TODO: aqui mando a guardar el doc */

        path_saved='documents/acaffdb6-75dc-4bb1-9463-01fe0d218258'


        const magazine: Magazine = {
          id: 0, 
          name: this.magazineForm.get('name')?.value,
          FK_User: 1 , 
          description: this.magazineForm.get('description')?.value,
          canComment: this.magazineForm.get('canComment')?.value,
          canLike: this.magazineForm.get('canLike')?.value,
          canSubscribe: this.magazineForm.get('canSubscribe')?.value,
          type: this.magazineForm.get('type')?.value,
          price: this.magazineForm.get('price')?.value,
          isEnabled: true,
          path: path_saved
        };
        console.log('GUARDANDOOOOO')

        this.userService.saveMagazine(magazine).subscribe({
          next: (response) => {
            console.log('Se Guardo la Revista', response);
          },
          error: (err) => {
            console.error('No se guardo la revista', err);
          }
        })


      } else {
        console.warn('No se ha seleccionado ningún archivo válido.');
      }

    } else {
      this.magazineForm.markAllAsTouched();
      console.log('cambios no validos')
    }
  }

}
