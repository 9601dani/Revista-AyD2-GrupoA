import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdTemplateComponent } from './ad-template.component';

describe('AdTemplateComponent', () => {
  let component: AdTemplateComponent;
  let fixture: ComponentFixture<AdTemplateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdTemplateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
