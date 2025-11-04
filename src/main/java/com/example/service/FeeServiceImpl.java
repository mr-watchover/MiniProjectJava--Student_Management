// In src/main/java/com/example/service/FeeServiceImpl.java
package com.example.service;

import com.example.dao.PaymentRepository;
import com.example.dao.StudentRepository;
import com.example.model.Payment;
import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class FeeServiceImpl implements FeeService {

    @Autowired
    private StudentRepository studentRepository; // Use Repository

    @Autowired
    private PaymentRepository paymentRepository; // Use Repository

    @Override
    @Transactional
    public void makePayment(int studentId, BigDecimal paymentAmount) {
        System.out.println("Attempting payment for student " + studentId + " of amount " + paymentAmount);
        
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        BigDecimal newBalance = student.getBalance().subtract(paymentAmount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient funds for student: " + student.getName());
        }

        student.setBalance(newBalance);
        studentRepository.save(student); // Use save()
        System.out.println("Successfully updated student balance.");

        Payment payment = new Payment(student, paymentAmount, LocalDateTime.now(), "PAYMENT");
        paymentRepository.save(payment); // Use save()
        System.out.println("Successfully logged payment.");
    }

    @Override
    @Transactional
    public void issueRefund(int studentId, BigDecimal refundAmount) {
        System.out.println("Issuing refund for student " + studentId + " of amount " + refundAmount);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        BigDecimal newBalance = student.getBalance().add(refundAmount);
        student.setBalance(newBalance);
        studentRepository.save(student); // Use save()
        System.out.println("Successfully updated student balance for refund.");

        Payment payment = new Payment(student, refundAmount, LocalDateTime.now(), "REFUND");
        paymentRepository.save(payment); // Use save()
        System.out.println("Successfully logged refund.");
    }
}