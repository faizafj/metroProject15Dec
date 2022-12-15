package com.maria.persistence;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maria.entity.Customer;



@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

	
}
