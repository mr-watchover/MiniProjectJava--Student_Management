package com.example.service;

import java.math.BigDecimal;

public interface FeeService {

    /**
     * Processes a fee payment for a student.
     * This operation must be atomic.
     */
    void makePayment(int studentId, BigDecimal amount);

    /**
     * Processes a refund for a student.
     * This operation must be atomic.
     */
    void issueRefund(int studentId, BigDecimal amount);
}