import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'image'
})
export class ImagePipe implements PipeTransform {

  bucket: string = 'https://storage.googleapis.com/bucket-magazines/';


  transform(value: string): string {
    return this.bucket + value;
  }

}
