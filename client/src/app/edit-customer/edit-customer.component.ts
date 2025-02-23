import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';

@Component({
  selector: 'app-edit-customer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-customer.component.html',
  styleUrl: './edit-customer.component.scss'
})
export class EditCustomerComponent {
  customerPhone: string = '';
  customerErrorMessage: string = '';

  customerID: string = '';
  fisrtName: string = '';
  lastName: string = '';
  email: string = '';
  phone: string = '';
  address: string = '';
  nic: string = '';

  private http = inject(HttpClient);

  onCusPhoneChange(customerPhone: string) {
    this.customerPhone = customerPhone;

    if (customerPhone.length === 10) {
      this.getCustomer();
    }

    if (customerPhone.length < 10) {
      this.customerID = '';
      this.fisrtName = '';
      this.lastName = '';
      this.email = '';
      this.phone = '';
      this.address = '';
      this.nic = '';
      this.customerErrorMessage = '';
    }
  }

  getCustomer() {
    const url =
      'http://localhost:8080/server_war_exploded/api/manage-customers/get-customer';
    const customerData = { phone: this.customerPhone };

    this.http
      .post<{ status: string; customer: any }>(url, customerData)
      .subscribe({
        next: (response) => {
          if (response.status === 'success') {
            this.customerID = response.customer.id;
            this.fisrtName = response.customer.firstName;
            this.lastName = response.customer.lastName;
            this.email = response.customer.email;
            this.phone = response.customer.phone;
            this.address = response.customer.address;
            this.nic = response.customer.nic;
          }
        },
        error: (error) => {
          console.error('Error:', error);
          this.customerErrorMessage = 'Customer Not Found!';
          // this.responseData = 'Error occurred';
        },
      });
  }
}
