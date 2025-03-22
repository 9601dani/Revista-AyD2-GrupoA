import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appNotProfile]'
})
export class NotProfileDirective {

  constructor(private _elementImg: ElementRef) { }

  @HostListener('error')
  onError() :void {
    this._elementImg.nativeElement.src = "default-profile.jpg";
  }

}
