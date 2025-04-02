import {AfterViewInit, Component, computed, inject, Inject, model, OnInit, signal, ViewChild} from '@angular/core';
import {NavbarComponent} from '../../commons/navbar/navbar.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import {provideMomentDateAdapter} from '@angular/material-moment-adapter';
import {AdsService} from '../../../services/ads/ads.service';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AdTemplateComponent} from '../../commons/ad-template/ad-template.component';
import Swal from 'sweetalert2';
import {DomSanitizer} from '@angular/platform-browser';
import {
  MatAutocompleteModule,
  MatAutocompleteSelectedEvent,
} from "@angular/material/autocomplete";
import {
  MatChipInputEvent,
  MatChipsModule
} from "@angular/material/chips";
import {Label} from '../../../models/Label.model';
import {capitalizeCategories, capitalizeLabels} from '../../../helpers/helpers';
import {Category} from '../../../models/Category.mode';
import {UserService} from '../../../services/user.service';
import {MatIconModule} from '@angular/material/icon';
import {LiveAnnouncer} from '@angular/cdk/a11y';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {CommonModule} from '@angular/common';
import moment from 'moment';
import {MatInputModule} from '@angular/material/input';
import {LocalStorageService} from '../../../services/local-storage.service';

export const MY_FORMATS = {
  parse: {
    dateInput: 'DD/MM/YYYY',
  },
  display: {
    dateInput: 'DD/MM/YYYY',
    monthYearLabel: 'DD/MM/YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'DD/MM/YYYY',
  },
};

@Component({
  selector: 'app-buy-ad',
  imports: [
    NavbarComponent,
    MatDatepickerModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    AdTemplateComponent,
    MatAutocompleteModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    FormsModule,
    CommonModule,
    MatInputModule
  ],
  providers: [provideMomentDateAdapter(MY_FORMATS)],

  templateUrl: './buy-ad.component.html',
  styleUrl: './buy-ad.component.scss',
  standalone: true
})
export class BuyAdComponent implements OnInit {

  @ViewChild('fileInput') fileInput: any;
  selectedFile: File | null = null;
  selectedPreviewImage: string | null = null;
  filename: any;
  currentType = "TEXT";
  adTypesList: any[] = [];
  categoriesList: any[] = [];
  labelsList: any[] = [];
  periodsList: any[] = [];

  periodsMap: any = {
    DAILY: "Un día",
    THREEDAYS: "Tres días",
    ONEWEEK: "Una semana",
    TWOWEEKS: "Dos semanas"
  }

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  categories = signal<Category[]>([]);
  currentCategorySignal = model('');

  readonly filteredCategories = computed(() => {
    const query = this.currentCategorySignal().toLowerCase();
    const selectedNames = this.categories().map((l) => l.name.toLowerCase());

    return query
      ? this.categoriesList.filter(
        (category) =>
          category.name.toLowerCase().includes(query) &&
          !selectedNames.includes(category.name.toLowerCase())
      )
      : this.categoriesList.filter(
        (category) => !selectedNames.includes(category.name.toLowerCase())
      );
  });

  labels = signal<Label[]>([]);
  currentLabelSignal = model('');
  readonly announcer = inject(LiveAnnouncer);

  readonly filteredLabels = computed(() => {
    const query = this.currentLabelSignal().toLowerCase();
    const selectedNames = this.labels().map((l) => l.name.toLowerCase());

    return query
      ? this.labelsList.filter(
        (label) =>
          label.name.toLowerCase().includes(query) &&
          !selectedNames.includes(label.name.toLowerCase())
      )
      : this.labelsList.filter(
        (label) => !selectedNames.includes(label.name.toLowerCase())
      );
  });

  showTemplate = true;

  adForm!: FormGroup;

  constructor(
    private _adsService: AdsService,
    private _userService: UserService,
    private _localStorageService: LocalStorageService,
    private _fb: FormBuilder,
    private sanitizer: DomSanitizer
  ) {
  }

  ngOnInit(): void {
    this.initForm();
    this.getAdTypes();
    this.getCategories();
    this.getPeriods();
    this.getLabels();
  }

  initForm() {
    this.adForm = this._fb.group({
      adType: [null, Validators.required],
      content: [''],
      period: [null, Validators.required],
      start: [null, Validators.required],
      end: [null, Validators.required],
      cost: [0]
    })

    this.adForm.get('adType')?.valueChanges.subscribe(value => {
      this.onTypeChange(value);
    });

    this.adForm.get('period')?.valueChanges.subscribe(period => {
      this.calculateCost(period);
    });

    this.adForm.get('start')?.valueChanges.subscribe(start => {
      const period = this.adForm.get('period')?.value;
      this.calculateCost(period)
    });

    this.adForm.get('end')?.disable();
    this.adForm.get('cost')?.disable();
  }

  calculateCost(periodId: any) {
    const period = this.periodsList.find(p => p.id == periodId);
    const startDate = this.adForm.get('start')?.value;

    if (!startDate) {
      return;
    }

    let endDate: any;

    switch (period.name) {
      case 'DAILY':
        endDate = moment(startDate).add(1, 'days');
        break;

      case 'THREEDAYS':
        endDate = moment(startDate).add(3, 'days');
        break;

      case 'ONEWEEK':
        endDate = moment(startDate).add(1, 'week');
        break;

      case 'TWOWEEKS':
        endDate = moment(startDate).add(2, 'week');
        break;

      default:
        endDate = moment(startDate);
        break;
    }

    this.adForm.patchValue({ end: endDate });
    this.adForm.patchValue({ cost: period.cost });
  }

  getAdTypes() {
    this._adsService.getAllAdTypes().subscribe({
      next: res => {
        this.adTypesList = res;
        this.adForm.patchValue({
          adType: this.adTypesList[0].id
        })
      },
      error: err => {
        console.error(err);
      }
    })
  }

  getCategories() {
    this._userService.getAllGategories().subscribe({
      next: (value: Category[]) => {
        this.categoriesList = capitalizeCategories(value);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  getPeriods() {
    this._adsService.getAllPeriods().subscribe({
      next: res => {
        console.log(res);
        this.periodsList = res;
        this.adForm.patchValue({
          period: this.periodsList[0].id,
          cost: this.periodsList[0].cost
        })
      },
      error: err => {
        console.error(err);
      }
    })
  }

  getLabels() {
    this._userService.getAllLabels().subscribe({
      next: (value: Label[]) => {
        console.log(value);
        this.labelsList = capitalizeLabels(value);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  onTypeChange(event: any) {
    this.showTemplate = false;
    this.adForm.patchValue({
      content: ""
    })

    const adType = this.adTypesList.find(ad => ad.id == event);
    this.currentType = adType.name;
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    const allowedTypes = ['image/jpeg', 'image/png', 'image/jpg'];

    if (file && allowedTypes.includes(file.type)) {
      this.selectedFile = file;

      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.selectedPreviewImage = e.target.result;
        this.adForm.patchValue({
          content: this.selectedPreviewImage
        })
      };
      reader.readAsDataURL(file);
    }

    console.log(this.selectedPreviewImage);
  }

  showPreview() {
    const { content } = this.adForm.value;

    if(!content) {
      Swal.fire({
        title: "Error!",
        text: "Por favor, ingrese el contenido antes de la previsualización.",
        icon: "error"
      })
      return;
    }

    if(this.currentType === "VIDEO") {
      const sanitizedContent = this.sanitizer.bypassSecurityTrustHtml(content)
      this.adForm.patchValue({
        content: sanitizedContent
      })
    }

    this.showTemplate = true;
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const name = event.option.viewValue;
    const label = this.labelsList.find((l) => l.name === name);
    if (!label) return;

    this.labels.update((labels) => [...labels, label]);
    this.currentLabelSignal.set('');
    event.option.deselect();
  }

  add(event: MatChipInputEvent, inputElement: HTMLInputElement): void {
    const value = (event.value || '').trim();
    if (!value) return;

    const alreadySelected = this.labels().some(
      (l) => l.name.toLowerCase() === value.toLowerCase()
    );
    if (alreadySelected) return;

    const existing = this.labelsList.find(
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

  addC(event: MatChipInputEvent, inputElement: HTMLInputElement): void {
    const value = (event.value || '').trim();
    if (!value) return;

    const alreadySelected = this.categories().some(
      (c) => c.name.toLowerCase() === value.toLowerCase()
    );
    if (alreadySelected) return;

    const existing = this.categoriesList.find(
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
    const category = this.categoriesList.find((c) => c.name === name);
    if (!category) return;

    this.categories.update((categories) => [...categories, category]);
    this.currentCategorySignal.set('');
    event.option.deselect();
  }

  save() {
    if(this.currentType === "IMAGE" && this.selectedFile === null) {
      Swal.fire({
        title: "Error!",
        text: "No se ha seleccionado un archivo",
        icon: "error"
      })
      return;
    }

    if(this.adForm.invalid) {
      Swal.fire({
        title: "Error!",
        text: "Por favor llene los campos",
        icon: "error"
      })
      return;
    }

    const { content, adType, category, period, start, end } = this.adForm.value;

    const data = new FormData();

    console.log(category);

    if(this.selectedFile) {
      data.append('file', this.selectedFile);
    }

    data.append('content', content);
    data.append('adType', adType);
    this.categories().forEach(category => {
      data.append('categories', String(category.name));
    });
    this.labels().forEach(label => {
      data.append('labels', String(label.name));
    })
    data.append('period', period);
    data.append('start', moment(start).format('YYYY-MM-DD'));
    data.append('end', moment(end).format('YYYY-MM-DD'));
    data.append('userId', this._localStorageService.getItem(this._localStorageService.USER_ID));

    this._adsService.saveAd(data).subscribe({
      next: res => {
        console.log(res);
        Swal.fire({
          title: "Éxito!",
          text: "Anuncio registrado con éxito.",
          icon: "success"
        })
      },
      error: err => {
        console.error(err);
      }
    });
  }
}
