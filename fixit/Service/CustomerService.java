package com.example.fixit.Service;

import com.example.fixit.Model.Customer;
import com.example.fixit.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void addAllCustomers(Customer customer) {

        customerRepository.save(customer);
    }

    public Boolean updateCustomer(Integer id, Customer customer) {
        Customer oldCustomer = customerRepository.findCustomerById(id);
        if (oldCustomer == null) {
            return false;
        }
        oldCustomer.setUserName(customer.getUserName());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setAddress(customer.getAddress());
        oldCustomer.setPhone(customer.getPhone());
        oldCustomer.setPassword(customer.getPassword());
        customerRepository.save(oldCustomer);
        return true;
    }

    public Boolean deleteCustomer(Integer id){
        Customer customer = customerRepository.findCustomerById(id);
        if (customer==null){
            return false;
        }
        customerRepository.delete(customer);
        return true;
    }

}
