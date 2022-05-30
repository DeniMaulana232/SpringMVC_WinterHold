package com.indocyber.SpringMVC.services;

import com.indocyber.SpringMVC.dtos.Customer.CustomerGridDto;
import com.indocyber.SpringMVC.dtos.Customer.UpsertCustomerDTO;
import com.indocyber.SpringMVC.models.Customer;
import com.indocyber.SpringMVC.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    private final int PAGE_LIMIT= 10;
    public Page<CustomerGridDto> findAllCustomer(String id, String name, Integer page){
        Pageable pagination = PageRequest.of(page -1, PAGE_LIMIT, Sort.by("id"));
        Page <CustomerGridDto> allCustomer = customerRepository.findAll(id, name, pagination);
        return allCustomer;
    }

    public void saveCustomer(UpsertCustomerDTO customerDTO){
        Customer customer = new Customer(
                customerDTO.getId(),
                customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getBirthDate(),
                customerDTO.getGender(),
                customerDTO.getPhone(),
                customerDTO.getAddress(),
                customerDTO.getMembershipExpireDate());

        customerRepository.save(customer);
    }

    public UpsertCustomerDTO getCustomerById(String id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return new UpsertCustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getBirthDate(),
                customer.getGender(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getMembershipExpireDate());
    }

    public void extendMembership(String id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setMembershipExpireDate(customer.getMembershipExpireDate().plusYears(2));
        customerRepository.save(customer);
    }

    public void deleteCustomerById(String id) {
        customerRepository.deleteById(id);
    }

    public List<CustomerGridDto> findAllCustomerNotExp() {
        return customerRepository.findAllCustomerNotExp();
    }

    public CustomerGridDto findAllCustomerByIdForDetail(String id) {
        CustomerGridDto customer = customerRepository.findAllCustomer(id);
        return customer;
    }
}
