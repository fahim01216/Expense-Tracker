package com.codewithfahim.expensetracker.service;

import com.codewithfahim.expensetracker.entity.Expense;
import com.codewithfahim.expensetracker.exception.ResourceNotFoundException;
import com.codewithfahim.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserService userService;

    @Override
    public Expense saveExpenseDetails(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepository.save(expense);
    }

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepository.findByUserId(userService.getLoggedInUser().getId(), page);
    }

    @Override
    public List<Expense> getExpensesByCategory(String category, Pageable page) {
        return expenseRepository.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
    }

    @Override
    public List<Expense> getExpensesByName(String keyword, Pageable page) {
        return expenseRepository.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page).toList();
    }

    @Override
    public List<Expense> getExpensesByDate(Date startDate, Date endDate, Pageable page) {
        if(startDate == null) {
            startDate = new Date(0);
        }
        if(endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        }
        return expenseRepository.findByUserIdAndCreatedAtBetween(userService.getLoggedInUser().getId(), startDate, endDate, page).toList();
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense =  expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
        if(expense.isPresent()) {
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense not found for the id: " + id);
    }

    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);
        existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
        existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());

        return expenseRepository.save(existingExpense);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id);
        throw new ResourceNotFoundException("Expense not found for the id: " + id);
    }
}
