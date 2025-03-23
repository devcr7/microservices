package com.shukldi.ecommerce.customer;

import com.shukldi.ecommerce.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest request) {
        Customer customer = customerRepository.save(customerMapper.toCustomer(request));
        return customer.getId();
    }

    public Object updateCustomer(@Valid CustomerRequest request) {
        Customer customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setAddress(request.address());
        customerRepository.save(customer);
        return customer;
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> CustomerResponse.builder()
                        .id(customer.getId())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .email(customer.getEmail())
                        .address(customer.getAddress())
                        .build())
                .toList();
    }

    public Boolean existsCustomer(String id) {
        return customerRepository.existsById(id);
    }

    public CustomerResponse findCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .build();
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
