package com.indocyber.SpringMVC.repositories;

import com.indocyber.SpringMVC.dtos.Customer.CustomerGridDto;
import com.indocyber.SpringMVC.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("""
            SELECT new com.indocyber.SpringMVC.dtos.Customer.CustomerGridDto
            (c.id, CONCAT(c.firstName,' ' ,c.lastName), c.birthDate, c.gender, c.phone, c.address, c.membershipExpireDate)
            FROM Customer c
            WHERE c.id LIKE %:id%
            AND CONCAT(c.firstName,' ' ,c.lastName) LIKE %:name%
            """)
    Page<CustomerGridDto> findAll(@Param("id") String id,
                                  @Param("name") String name,
                                  Pageable pageable);

    @Query("""
            SELECT new com.indocyber.SpringMVC.dtos.Customer.CustomerGridDto
            (e.id, CONCAT(e.firstName,' ' ,e.lastName), e.birthDate, e.gender, e.phone, e.address, e.membershipExpireDate)
            FROM Customer e
            WHERE e.membershipExpireDate >= GETDATE()
            """)
    List<CustomerGridDto> findAllCustomerNotExp();

    @Query("""
            SELECT new com.indocyber.SpringMVC.dtos.Customer.CustomerGridDto
            (c.id, CONCAT(c.firstName, ' ', c.lastName), c.birthDate, c.gender, c.phone, c.address, c.membershipExpireDate )
            FROM Customer c
            WHERE c.id = :id
            """)
    CustomerGridDto findAllCustomer(String id);
}
