import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-ad-template',
  imports: [],
  templateUrl: './ad-template.component.html',
  styleUrl: './ad-template.component.scss',
  standalone: true
})
export class AdTemplateComponent {

  @Input() showButton: boolean = true;
  @Input() data: any;
  @Input() type: any = "TEXT";
}
