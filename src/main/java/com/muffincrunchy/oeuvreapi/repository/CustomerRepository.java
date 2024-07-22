package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query(value = "SELECT * FROM customers", nativeQuery = true)
    List<Customer> getAllCustomers();

    @Query(value = "SELECT * FROM customers WHERE id = :id", nativeQuery = true)
    Optional<Customer> getCustomerById(@Param("id") String id);

    @Modifying
    @Query(value = "INSERT INTO customers (id, name, birth_date, phone_no, email) VALUES (:id, :name, :birth_date, :phone_no, :email)", nativeQuery = true)
    int createCustomer(@Param("id") String id, @Param("name") String name, @Param("birth_date") Date birthDate, @Param("phone_no") String phoneNo, @Param("email") String email);

    @Modifying
    @Query(value = "UPDATE customers SET name = :name, birth_date = :birth_date, phone_no = :phone_no, email = :email WHERE id = :id", nativeQuery = true)
    int updateCustomerById(@Param("id") String id, @Param("name") String name, @Param("birth_date") Date birthDate, @Param("phone_no") String phoneNo, @Param("email") String email);

    @Modifying
    @Query(value = "DELETE FROM customers WHERE id = :id", nativeQuery = true)
    void deleteCustomerById(@Param("id") String id);
}
