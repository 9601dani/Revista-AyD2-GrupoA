import { TestBed } from '@angular/core/testing';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { PreviewDocumentPipe } from './preview-document.pipe';

describe('PreviewDocumentPipe', () => {
  let pipe: PreviewDocumentPipe;
  let sanitizer: DomSanitizer;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    sanitizer = TestBed.inject(DomSanitizer);
    pipe = new PreviewDocumentPipe(sanitizer);
  });

  it('should create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('should transform a document path into a safe preview URL with PDF params', () => {
    const file = 'revistas/test.pdf';
    const expectedUrl = 'https://storage.googleapis.com/bucket-magazines/revistas/test.pdf#page=1&zoom=50&scrollbar=0&toolbar=0&navpanes=0';

    const result: SafeResourceUrl = pipe.transform(file);

    expect(result).toBeTruthy();
    expect(result.toString()).toContain(expectedUrl);
  });

  it('should not append params if value already contains a hash (#)', () => {
    const file = 'revistas/test.pdf#page=2';
    const expectedUrl = 'https://storage.googleapis.com/bucket-magazines/revistas/test.pdf#page=2';

    const result: SafeResourceUrl = pipe.transform(file);

    expect(result.toString()).toContain(expectedUrl);
    expect(result.toString()).not.toContain('&zoom=50');
  });
});
