import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {NavbarComponent} from '../../commons/navbar/navbar.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import {provideMomentDateAdapter} from '@angular/material-moment-adapter';
import {AdsService} from '../../../services/ads/ads.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AdTemplateComponent} from '../../commons/ad-template/ad-template.component';
import Swal from 'sweetalert2';
import {DomSanitizer} from '@angular/platform-browser';

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
    AdTemplateComponent
  ],
  providers: [provideMomentDateAdapter(MY_FORMATS)],

  templateUrl: './buy-ad.component.html',
  styleUrl: './buy-ad.component.scss',
  standalone: true
})
export class BuyAdComponent implements OnInit, AfterViewInit {

  @ViewChild('fileInput') fileInput: any;
  selectedFile: File | null = null;
  selectedPreviewImage: string | null = null;
  filename: any;
  currentType = "TEXT";
  adTypesList: any[] = [];
  categoriesList: any[] = [];
  periodsList: any[] = [];

  showTemplate = true;

  adForm!: FormGroup;

  constructor(
    private _adsService: AdsService,
    private _fb: FormBuilder,
    private sanitizer: DomSanitizer
  ) {
  }

  ngOnInit(): void {
    this.initForm();
    this.getAdTypes();
    this.getCategories();
    this.getPeriods();
  }

  initForm() {
    this.adForm = this._fb.group({
      adType: [null, Validators.required],
      content: [''],
      category: [null, Validators.required],
      period: [null, Validators.required],
      start: [null, Validators.required],
      end: [null, Validators.required]
    })

    this.adForm.get('adType')?.valueChanges.subscribe(value => {
      this.onTypeChange(value);
    });
  }

  getAdTypes() {
    this._adsService.getAllAdTypes().subscribe({
      next: res => {
        console.log(res);
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
    this._adsService.getAllCategories().subscribe({
      next: res => {
        console.log(res);
        this.categoriesList = res;
        this.adForm.patchValue({
          category: this.categoriesList[0].id
        })
      },
      error: err => {
        console.error(err);
      }
    })
  }

  getPeriods() {
    this._adsService.getAllPeriods().subscribe({
      next: res => {
        console.log(res);
        this.periodsList = res;
        this.adForm.patchValue({
          period: this.periodsList[0].id
        })
      },
      error: err => {
        console.error(err);
      }
    })
  }

  ngAfterViewInit(): void {
    // const element = document.getElementById('rangePicker');
    //
    // if (element) {
    //   const calendar = bulmaCalendar.attach(element, {
    //     type: 'date',
    //     isRange: true,
    //     dateFormat: 'MM/DD/YYYY',
    //     displayMode: 'dialog',
    //     showHeader: false,
    //     closeOnSelect: false,
    //     color: 'primary',
    //     startDate: new Date(),
    //   })[0];
    //
    //   calendar.on('select', (data: any) => {
    //     console.log('Rango seleccionado:');
    //     console.log('Desde:', data.start);
    //     console.log('Hasta:', data.end);
    //   });
    // }
  }

  onTypeChange(event: any) {
    this.showTemplate = false;
    this.adForm.patchValue({
      content: ""
    })

    const adType = this.adTypesList.find(ad => ad.id == event);
    this.currentType = adType.name;
  }

  triggerFileInput() {
    this.fileInput.nativeElement.click();
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

    data.append('file', this.selectedFile!);

    data.append('content', content);
    data.append('adType', adType);
    data.append('category', category);
    data.append('period', period);
    data.append('start', start.toISOString().split('T')[0]);
    data.append('end', end.toISOString().split('T')[0]);

    this._adsService.saveAd(data).subscribe({
      next: res => {
        console.log(res);
      },
      error: err => {
        console.error(err);
      }
    });
  }
}
