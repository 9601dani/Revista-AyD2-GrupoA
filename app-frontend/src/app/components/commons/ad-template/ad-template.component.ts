import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {ImagePipe} from '../../../pipes/image.pipe';

@Component({
  selector: 'app-ad-template',
  imports: [
    ImagePipe
  ],
  templateUrl: './ad-template.component.html',
  styleUrl: './ad-template.component.scss',
  standalone: true
})
export class AdTemplateComponent implements OnChanges {

  @Input() showButton: boolean = false;
  @Input() usePipe: boolean = true;
  @Input() data: any;
  @Input() type: any = "TEXT";

  @Output() closeAd = new EventEmitter<void>()

  sanitizedUrl: SafeResourceUrl | null = null;

  constructor(private sanitizer: DomSanitizer) {}

  ngOnChanges(): void {
    if (this.type === 'VIDEO' && this.data) {
      const embedUrl = this.toEmbedUrl(this.data);
      this.sanitizedUrl = this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl);
    }
  }

  private toEmbedUrl(url: string): string {
    let videoId = '';

    if (url.includes('v=')) {
      videoId = url.split('v=')[1];
    } else if (url.includes('youtu.be/')) {
      videoId = url.split('youtu.be/')[1];
    }

    return `https://www.youtube.com/embed/${videoId}?autoplay=1&mute=1`;
  }

  close() {
    this.closeAd.emit();
  }
}
