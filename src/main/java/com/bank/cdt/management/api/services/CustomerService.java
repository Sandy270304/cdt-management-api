package com.bank.cdt.management.api.services;

import com.bank.cdt.management.api.models.Customer;
import com.bank.cdt.management.api.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByDocumentNumber(customer.getDocumentNumber())) {
            throw new RuntimeException("A customer already exists with document number: " + customer.getDocumentNumber());
        }

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = findCustomerById(id);

        existingCustomer.setDocumentType(updatedCustomer.getDocumentType());
        existingCustomer.setDocumentNumber(updatedCustomer.getDocumentNumber());
        existingCustomer.setFirstName(updatedCustomer.getFirstName());
        existingCustomer.setLastName(updatedCustomer.getLastName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        existingCustomer.setActive(updatedCustomer.getActive());

        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        Customer existingCustomer = findCustomerById(id);
        customerRepository.delete(existingCustomer);
    }
}
