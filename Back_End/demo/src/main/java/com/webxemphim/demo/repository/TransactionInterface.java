package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface TransactionInterface extends CrudRepository<Transaction, Integer> {

    // Tìm tất cả các giao dịch hợp lệ (endDate >= currentDate) của user
    @Query("SELECT t FROM transaction t WHERE t.user.id = ?1 AND t.endDate >= ?2 ORDER BY t.endDate DESC")
    List<Transaction> findValidTransactions(int userId, Date currentDate);
}
