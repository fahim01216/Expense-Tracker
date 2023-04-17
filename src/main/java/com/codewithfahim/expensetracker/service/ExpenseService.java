package com.codewithfahim.expensetracker.service;

import com.codewithfahim.expensetracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface ExpenseService {

    Expense saveExpenseDetails(Expense expense);
    Page<Expense> getAllExpenses(Pageable page);
    List<Expense> getExpensesByCategory(String category, Pageable page);
    List<Expense> getExpensesByName(String keyword, Pageable page);
    List<Expense> getExpensesByDate(Date startDate, Date endDate, Pageable page);
    Expense getExpenseById(Long id);

    Expense updateExpenseDetails(Long id, Expense expense);

    void deleteExpenseById(Long id);
}
