package com.codewithfahim.expensetracker.repository;

import com.codewithfahim.expensetracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByCategory(String category, Pageable page);
    Page<Expense> findByNameContaining(String keyword, Pageable page);
    Page<Expense> findByCreatedAtBetween(Date startDate, Date endDate, Pageable page);
}
