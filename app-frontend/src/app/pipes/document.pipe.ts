import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Pipe({
  name: 'document',
  standalone: true
})
export class DocumentPipe implements PipeTransform {

  private bucket = 'https://storage.googleapis.com/bucket-magazines/';

  constructor(private sanitizer: DomSanitizer) {}

  transform(value: string): SafeResourceUrl {
    const fullUrl = this.bucket + value;
    return this.sanitizer.bypassSecurityTrustResourceUrl(fullUrl);
  }


}
