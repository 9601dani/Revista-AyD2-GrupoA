import { Component, computed, Inject, inject, model, PLATFORM_ID, signal } from '@angular/core';
import { NavbarComponent } from '../../commons/navbar/navbar.component';
import { UserService } from '../../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule, isPlatformServer } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import Swal from 'sweetalert2';
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Label } from '../../../models/Label.model';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Category } from '../../../models/Category.mode';
import { capitalizeCategories, capitalizeLabels } from '../../../helpers/helpers';
import { AllMagazineResponse } from '../../../models/AllMagazine.mode';
import { LocalStorageService } from '../../../services/local-storage.service';

@Component({
  selector: 'app-edit-magazine',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NavbarComponent,
    CommonModule,
    FormsModule,
    MatAutocompleteModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule
  ],
  templateUrl: './edit-magazine.component.html',
  styleUrl: './edit-magazine.component.scss'
})
export class EditMagazineComponent {

  types = [
    { label: 'Gratis', value: 'FREE' },
    { label: 'De Pago', value: 'PAID' }
  ];
  

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

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

  categories = signal<Category[]>([]);
  currentCategorySignal = model('');
  allCategories: Category[] = [];

  readonly filteredCategories = computed(() => {
    const query = this.currentCategorySignal().toLowerCase();
    const selectedNames = this.categories().map((c) => c.name.toLowerCase());
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

  magazineForm!: FormGroup;
  pdfPreviewUrl: any = null;
  magazineId!: number;
  magazine!: AllMagazineResponse
  


  constructor(
    private _userService: UserService,
    private _localStorage: LocalStorageService,
    private _route: ActivatedRoute,
    private _fb: FormBuilder,
    private _sanitizer: DomSanitizer,
    private _router:Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit() {
    if (isPlatformServer(this.platformId)) {
      return;
    }

    this._route.params.subscribe(params => {
      if (params['id']) {
        this.magazineId = +params['id'];
        this.initForm();
        this.loadMagazineData(this.magazineId);
      }
    });

    this._userService.getAllLabels().subscribe({
      next: (value: Label[]) => {
        this.allLabels = capitalizeLabels(value);
      },
      error: (err) => console.log(err),
    });
    
    this._userService.getAllGategories().subscribe({
      next: (value: Category[]) => {
        this.allCategories = capitalizeCategories(value);
      },
      error: (err) => console.log(err),
    });
    
  }

  initForm() {
    this.magazineForm = this._fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      canComment: [false],
      canLike: [false],
      canSubscribe: [false],
      type: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      file: [null]
    });
  }

  loadMagazineData(id: number) {
    this._userService.getMagazineById(id).subscribe((magazine: AllMagazineResponse) => {

      console.log("MAGAZINEEEEE ", magazine)
      this.magazine = magazine
      this.magazineForm.patchValue({
        name: magazine.name,
        description: magazine.description,
        canComment: magazine.canComment,
        canLike: magazine.canLike,
        canSubscribe: magazine.canSubscribe,
        type: magazine.type,
        price: magazine.price
      });

      const path = magazine.documents[0].path;
      const fullUrl = `https://storage.googleapis.com/bucket-magazines/${path}`;
      this.pdfPreviewUrl = this._sanitizer.bypassSecurityTrustResourceUrl(fullUrl);
      
      

      this.labels.set(capitalizeLabels(this.magazine.labels || []));
      this.categories.set(capitalizeCategories(this.magazine.categories || []));
    });
  }
  

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (file) {
      this.magazineForm.patchValue({ file });
      this.pdfPreviewUrl = this._sanitizer.bypassSecurityTrustResourceUrl(URL.createObjectURL(file));
    }
  }

  onSubmit() {
    if (this.magazineForm.invalid) {
      Swal.fire({
        icon: 'error',
        title: 'Formulario inválido',
        text: 'Por favor completa todos los campos obligatorios.'
      });
      return;
    }

    const formValues = this.magazineForm.value;
    const formData = new FormData();

        formData.append('id', this.magazine.id.toString());
        formData.append('name', this.magazineForm.get('name')?.value);
        formData.append('FK_User', this._localStorage.getItem("user_id") || '');
        formData.append('description', this.magazineForm.get('description')?.value);
        formData.append('canComment', this.magazineForm.get('canComment')?.value);
        formData.append('canLike', this.magazineForm.get('canLike')?.value);
        formData.append('canSubscribe', this.magazineForm.get('canSubscribe')?.value);
        formData.append('type', this.magazineForm.get('type')?.value);
        formData.append('price', this.magazineForm.get('price')?.value);
        formData.append('isEnabled', 'true');
        
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

        console.log(FormData)
    this._userService.updateMagazine(formData).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Actualizado',
          text: 'La revista ha sido editada exitosamente.'
        });
      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudo actualizar la revista.'
        });
      }
    });
  }

  clearData() {
    this._router.navigate(['editor/my-magazines'])
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

  changePdfPreview(path: string) {
    const fullUrl = `https://storage.googleapis.com/bucket-magazines/${path}`;
    this.pdfPreviewUrl = this._sanitizer.bypassSecurityTrustResourceUrl(fullUrl);
  }
  
  
}
