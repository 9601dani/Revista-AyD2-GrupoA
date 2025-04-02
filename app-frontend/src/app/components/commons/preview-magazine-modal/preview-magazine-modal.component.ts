import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DocumentPipe } from '../../../pipes/document.pipe';
import { CommonModule } from '@angular/common';
import { PreviewDocumentPipe } from '../../../pipes/preview-document.pipe';
import { capitalizeCategories, capitalizeLabels } from '../../../helpers/helpers';

@Component({
  selector: 'app-preview-magazine-modal',
  imports: [PreviewDocumentPipe, CommonModule],
  templateUrl: './preview-magazine-modal.component.html',
  styleUrl: './preview-magazine-modal.component.scss'
})
export class PreviewMagazineModalComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  getCategoryNames(): string {

    this.data.categories = capitalizeCategories(this.data.categories);

    return this.data.categories.map((c: any) => c.name).join(', ');
  }
  
  getLabelNames(): string {

    this.data.labels = capitalizeLabels(this.data.labels);

    return this.data.labels.map((l: any) => l.name).join(', ');
  }
  
}
