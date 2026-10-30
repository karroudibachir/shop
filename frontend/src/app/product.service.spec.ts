import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ProductService } from './product.service';

describe('ProductService',()=>{
 let service:ProductService;let http:HttpTestingController;
 beforeEach(()=>{TestBed.configureTestingModule({providers:[ProductService,provideHttpClient(),provideHttpClientTesting()]});service=TestBed.inject(ProductService);http=TestBed.inject(HttpTestingController)});
 afterEach(()=>http.verify());
 it('sends catalog filters as query parameters',()=>{service.list({page:2,size:12,q:'lamp',sort:'price'}).subscribe(result=>expect(result.totalElements).toBe(1));const request=http.expectOne(r=>r.url==='/api/products'&&r.params.get('q')==='lamp'&&r.params.get('page')==='2'&&r.params.get('sort')==='price');request.flush({content:[],totalElements:1,totalPages:1,number:2,size:12})});
});
