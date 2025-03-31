import { CommonModule } from '@angular/common';
import { Component, computed, inject, input, model, signal } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import { UserService } from '../../../services/user.service';
import { Magazine } from '../../../models/Magazine.model';
import { LocalStorageService } from '../../../services/local-storage.service';
import Swal from 'sweetalert2';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { Label } from '../../../models/Label.model';
import { capitalizeCategories, capitalizeLabels } from '../../../helpers/helpers';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Category } from '../../../models/Category.mode';

@Component({
  selector: 'app-add-magazine',
  standalone:true,
  imports: [
    ReactiveFormsModule,
    NavbarComponent,
    CommonModule,
    FormsModule,
    MatAutocompleteModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
  ],
  templateUrl: './add-magazine.component.html',
  styleUrl: './add-magazine.component.scss'
})
export class AddMagazineComponent {

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  

  magazineForm!: FormGroup
  magazineToSave!: Magazine
  pdfPreviewUrl: SafeResourceUrl | null = null;

  /* Labels */
  labels = signal<Label[]>([]);
  currentLabelSignal = model('');
  readonly announcer = inject(LiveAnnouncer);
  allLabels: Label[] = [];

  readonly filteredLabels = computed(() => {
    const query = this.currentLabelSignal().toLowerCase();
    const selectedNames = this.labels().map((l) => l.name.toLowerCase());

    return query
      ? this.allLabels.filter(
          (label) =>
            label.name.toLowerCase().includes(query) &&
            !selectedNames.includes(label.name.toLowerCase())
        )
      : this.allLabels.filter(
          (label) => !selectedNames.includes(label.name.toLowerCase())
        );
  });

  /* Categories */
  categories = signal<Category[]>([]);
  currentCategorySignal = model('');
  allCategories: Category[] = [];

  readonly filteredCategories = computed(() => {
    const query = this.currentCategorySignal().toLowerCase();
    const selectedNames = this.categories().map((l) => l.name.toLowerCase());

    return query
      ? this.allCategories.filter(
          (category) =>
            category.name.toLowerCase().includes(query) &&
            !selectedNames.includes(category.name.toLowerCase())
        )
      : this.allCategories.filter(
          (category) => !selectedNames.includes(category.name.toLowerCase())
        );
  });


  types = [
    { label: 'Gratis', value: 'FREE' },
    { label: 'De Pago', value: 'PAID' }
  ];
  

  constructor( private fb: FormBuilder, private _userService: UserService, private _localStorage:LocalStorageService, private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
  ){   
  }

  ngOnInit(): void {
    this.magazineForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      canComment: [false],
      canLike: [false],
      canSubscribe: [false],
      type: ['', Validators.required],
      price: [null, [Validators.required, Validators.min(0)]],
      file: [null, Validators.required]

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
    this._userService.getAllLabels().subscribe({
          next: (value: Label[]) => {
            this.allLabels = capitalizeLabels(value);
          },
          error: (err) => {
            console.log(err);
          },
    });

    this._userService.getAllGategories().subscribe({
      next: (value: Category[]) => {
        this.allCategories = capitalizeCategories(value);
      },
      error: (err) => {
        console.log(err);
      },
});

/*     this.userService.getMagazineByIdUser(this.localStorage.getItem("user_id")).subscribe({
      next: (response) => {
        console.log(response)
      }, error: (err) => {
        console.log(err)
      }

    }) */
  }

  loadMagazineData(id: number) {
    this._userService.getMagazineById(id).subscribe((magazine: Magazine) => {
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
      this.magazineForm.patchValue({ file: file });
      this.magazineForm.get('file')?.updateValueAndValidity();
  
      const url = URL.createObjectURL(file);
      this.pdfPreviewUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
    } else {
      this.magazineForm.patchValue({ archivo: null });
      this.magazineForm.get('file')?.setErrors({ invalidType: true });
      this.pdfPreviewUrl = null;
    }
  }
  
  

  onSubmit(): void {
    if (this.magazineForm.valid) {

      console.log(this.magazineForm.value)
      let path_saved:string=''
      
      const file: File | null = this.magazineForm.get('file')?.value;

      if (file) {
        const formData = new FormData();

        formData.append('id', '0');
        formData.append('name', this.magazineForm.get('name')?.value);
        formData.append('FK_User', this._localStorage.getItem("user_id") || '');
        formData.append('description', this.magazineForm.get('description')?.value);
        formData.append('canComment', this.magazineForm.get('canComment')?.value);
        formData.append('canLike', this.magazineForm.get('canLike')?.value);
        formData.append('canSubscribe', this.magazineForm.get('canSubscribe')?.value);
        formData.append('type', this.magazineForm.get('type')?.value);
        formData.append('price', this.magazineForm.get('price')?.value);
        formData.append('isEnabled', 'true');
        formData.append('path', path_saved);
        
        const file: File | null = this.magazineForm.get('file')?.value;
        if (file) {
          formData.append('file', file, file.name);
        }
        
        this.labels().forEach((label, index) => {
          formData.append(`labels[${index}].name`, label.name);
        });
        
        this.categories().forEach((category, index) => {
          formData.append(`categories[${index}].name`, category.name);
        });
        
        
        this._userService.saveMagazine(formData).subscribe({
          next: (response) => {
            Swal.fire({
              icon: 'success',
              title: '¡Revista guardada!',
              text: 'La revista se ha guardado correctamente.',
              confirmButtonText: 'Aceptar'
              
            }).then(() =>{
              this.magazineForm.reset()
            });
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

  add(event: MatChipInputEvent, inputElement: HTMLInputElement): void {
    const value = (event.value || '').trim();
    if (!value) return;
  
    const alreadySelected = this.labels().some(
      (l) => l.name.toLowerCase() === value.toLowerCase()
    );
    if (alreadySelected) return;
  
    const existing = this.allLabels.find(
      (l) => l.name.toLowerCase() === value.toLowerCase()
    );
  
    const newLabel: Label = existing ?? { id: '', name: value };
    this.labels.update((labels) => [...labels, newLabel]);
  
    inputElement.value = '';
    this.currentLabelSignal.set('');
  }
  

  remove(label: Label): void {
    this.labels.update((labels) => {
      const filtered = labels.filter((l) => l.name !== label.name);
      this.announcer.announce(`Removed ${label.name}`);
      return filtered;
    });
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const name = event.option.viewValue;
    const label = this.allLabels.find((l) => l.name === name);
    if (!label) return;

    this.labels.update((labels) => [...labels, label]);
    this.currentLabelSignal.set('');
    event.option.deselect();
  }

  /* Categories */
  addC(event: MatChipInputEvent, inputElement: HTMLInputElement): void {
    const value = (event.value || '').trim();
    if (!value) return;
  
    const alreadySelected = this.categories().some(
      (c) => c.name.toLowerCase() === value.toLowerCase()
    );
    if (alreadySelected) return;
  
    const existing = this.allCategories.find(
      (c) => c.name.toLowerCase() === value.toLowerCase()
    );
  
    const newCategory: Category = existing ?? { id: '', name: value };
    this.categories.update((categories) => [...categories, newCategory]);
  
    inputElement.value = '';
    this.currentCategorySignal.set('');
  }
  

  removeC(category: Category): void {
    this.categories.update((categories) => {
      const filtered = categories.filter((c) => c.name !== category.name);
      this.announcer.announce(`Removed ${category.name}`);
      return filtered;
    });
  }

  selectedC(event: MatAutocompleteSelectedEvent): void {
    const name = event.option.viewValue;
    const category = this.allCategories.find((c) => c.name === name);
    if (!category) return;

    this.categories.update((categories) => [...categories, category]);
    this.currentCategorySignal.set('');
    event.option.deselect();
  }
  

}
