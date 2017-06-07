var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { Router, ActivatedRoute } from '@angular/router';
var CustomerDetailsComponent = (function () {
    function CustomerDetailsComponent(customerService, router, route) {
        this.customerService = customerService;
        this.router = router;
        this.route = route;
    }
    CustomerDetailsComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.id = this.route.snapshot.params['id'];
        this.customerService.getCustomer(this.id).subscribe(function (customer) {
            _this.customer = customer;
        });
    };
    return CustomerDetailsComponent;
}());
CustomerDetailsComponent = __decorate([
    Component({
        selector: 'app-customer-details',
        templateUrl: './customer-details.component.html',
        styleUrls: ['./customer-details.component.css']
    }),
    __metadata("design:paramtypes", [CustomerService, Router, ActivatedRoute])
], CustomerDetailsComponent);
export { CustomerDetailsComponent };
//# sourceMappingURL=../../../../../src/app/components/customer-details/customer-details.component.js.map