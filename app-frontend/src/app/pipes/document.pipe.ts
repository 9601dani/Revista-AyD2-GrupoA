import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'document'
})
export class DocumentPipe implements PipeTransform {

  bucket: string = 'https://storage.googleapis.com/bucket-magazines/';


  transform(value: string): string {
    return this.bucket + value;
  }


}
