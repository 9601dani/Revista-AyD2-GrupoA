import { TestBed } from '@angular/core/testing';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { DocumentPipe } from './document.pipe';

describe('DocumentPipe', () => {
  let pipe: DocumentPipe;
  let sanitizer: DomSanitizer;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    sanitizer = TestBed.inject(DomSanitizer);
    pipe = new DocumentPipe(sanitizer);
  });

  it('should create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('should transform a filename into a safe URL', () => {
    const testFile = 'revistas/revista1.pdf';
    const expectedUrl = 'https://storage.googleapis.com/bucket-magazines/revistas/revista1.pdf';

    const result: SafeResourceUrl = pipe.transform(testFile);

    expect(result).toBeTruthy();
    expect(result.toString()).toContain(expectedUrl);
  });
});
