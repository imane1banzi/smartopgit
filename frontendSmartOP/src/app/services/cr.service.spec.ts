import { TestBed } from '@angular/core/testing';

import { CRService } from './cr.service';

describe('CRService', () => {
  let service: CRService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CRService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
