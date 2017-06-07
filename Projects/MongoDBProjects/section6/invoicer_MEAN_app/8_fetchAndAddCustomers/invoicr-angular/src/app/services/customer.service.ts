import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class CustomerService {

  constructor(private http: Http) { }

  getCustomers(){
    return this.http.get('http://localhost:3000/api/customers')
      .map(res => res.json());
  }

  saveCustomer(customer){
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post('http://localhost:3000/api/customers', customer, {headers: headers})
      .map(res => res.json());
  }

}
