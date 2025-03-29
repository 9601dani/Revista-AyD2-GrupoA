import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import { UserService } from '../../../services/user.service';
import { Magazine } from '../../../models/Magazine.model';
import { LocalStorageService } from '../../../services/local-storage.service';
import Swal from 'sweetalert2';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { response } from 'express';

@Component({
  selector: 'app-add-magazine',
  imports: [CommonModule, ReactiveFormsModule, NavbarComponent ],
  templateUrl: './add-magazine.component.html',
  styleUrl: './add-magazine.component.scss'
})
export class AddMagazineComponent {

  magazineForm!: FormGroup
  magazineToSave!: Magazine
  pdfPreviewUrl: SafeResourceUrl | null = null;


  types = [
    { label: 'Gratis', value: 'FREE' },
    { label: 'De Pago', value: 'PAID' }
  ];
  

  constructor( private fb: FormBuilder, private userService: UserService, private localStorage:LocalStorageService, private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
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
      archivo: [null, Validators.required]

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

    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.loadMagazineData(id);
      }
    });

    this.userService.getMagazineByIdUser(1).subscribe({
      next: (response) => {
        console.log(response)
      }

    })
  }

  loadMagazineData(id: number) {
    this.userService.getMagazineById(id).subscribe((magazine: Magazine) => {
      this.magazineToSave = magazine;
      this.magazineForm.patchValue({
        name: magazine.name,
        description: magazine.description,
        canComment: magazine.canComment,
        canLike: magazine.canLike,
        canSubscribe: magazine.canSubscribe,
        type: magazine.type,
        price: magazine.price
      });
  
      if (magazine.path) {
        const existingPdfUrl = 'URL_BASE/' + magazine.path;
        this.pdfPreviewUrl = this.sanitizer.bypassSecurityTrustResourceUrl(existingPdfUrl);
      }
    });
  }
  
  

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file && file.type === 'application/pdf') {
      this.magazineForm.patchValue({ archivo: file });
      this.magazineForm.get('archivo')?.updateValueAndValidity();
  
      const url = URL.createObjectURL(file);
      this.pdfPreviewUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
    } else {
      this.magazineForm.patchValue({ archivo: null });
      this.magazineForm.get('archivo')?.setErrors({ invalidType: true });
      this.pdfPreviewUrl = null;
    }
  }
  
  

  onSubmit(): void {
    if (this.magazineForm.valid) {

      console.log(this.magazineForm.value)
      let path_saved:string=''
      
      const file: File | null = this.magazineForm.get('archivo')?.value;

      if (file) {
        const magazine: Magazine = {
          id: 0, 
          name: this.magazineForm.get('name')?.value,
          FK_User: this.localStorage.getItem("user_id") , 
          description: this.magazineForm.get('description')?.value,
          canComment: this.magazineForm.get('canComment')?.value,
          canLike: this.magazineForm.get('canLike')?.value,
          canSubscribe: this.magazineForm.get('canSubscribe')?.value,
          type: this.magazineForm.get('type')?.value,
          price: this.magazineForm.get('price')?.value,
          isEnabled: true,
          path: path_saved
        };

        this.userService.saveMagazine(magazine, file).subscribe({
          next: (response) => {
            Swal.fire({
              icon: 'success',
              title: '¡Revista guardada!',
              text: 'La revista se ha guardado correctamente.',
              confirmButtonText: 'Aceptar'
              
            }).then(this.magazineForm.reset);
          },
          error: (err) => {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'No se pudo guardar la revista. Intenta nuevamente.',
              confirmButtonText: 'Cerrar'
            });
            console.error(err);
          }
        })


      } else {
        Swal.fire({
          icon: 'warning',
          title: 'Falta de Archivo',
          text: 'Recuerda seleccionar un archivo',
        })
      }

    } else {
      this.magazineForm.markAllAsTouched();
      Swal.fire({
        icon: 'warning',
        title: 'Campos Vacios',
        text: 'Recuerda que debes llenar los campos obligatorios',
      })
    }
  }

  clearData(){
    this.magazineForm.reset;
    this.pdfPreviewUrl = null;
  }

}
