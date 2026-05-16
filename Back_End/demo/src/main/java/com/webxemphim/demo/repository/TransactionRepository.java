package com.webxemphim.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webxemphim.demo.entity.Transaction;
import com.webxemphim.demo.entity.User;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
    Optional<Transaction> findFirstByUserOrderByEndDateDesc(User user);
    List<Transaction> findByUser(User user);
}
