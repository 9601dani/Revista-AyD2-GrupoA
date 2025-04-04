import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AdsService } from './ads.service';

describe('AdsService', () => {
  let service: AdsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AdsService]
    });
    service = TestBed.inject(AdsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
