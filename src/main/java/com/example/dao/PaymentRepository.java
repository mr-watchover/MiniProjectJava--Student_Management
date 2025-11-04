// In src/main/java/com/example/dao/PaymentRepository.java
package com.example.dao;

import com.example.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}