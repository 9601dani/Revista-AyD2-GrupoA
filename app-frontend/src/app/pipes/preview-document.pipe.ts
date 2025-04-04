import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Pipe({
  name: 'previewDocument'
})
export class PreviewDocumentPipe implements PipeTransform {

  constructor(private sanitizer: DomSanitizer) {}
  bucket: string = 'https://storage.googleapis.com/bucket-magazines/';
  
  transform(value: string): SafeResourceUrl {
    
    value = this.bucket + value;
    
    const safeUrl = value.includes('#') ? value : `${value}#page=1&zoom=50&scrollbar=0&toolbar=0&navpanes=0`;
    return this.sanitizer.bypassSecurityTrustResourceUrl(safeUrl);
  }

}
