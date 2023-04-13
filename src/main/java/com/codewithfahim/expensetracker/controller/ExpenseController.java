package com.codewithfahim.expensetracker.controller;

import com.codewithfahim.expensetracker.entity.Expense;
import com.codewithfahim.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/expenses")
    public ResponseEntity<Expense> saveExpenseDetails(@RequestBody Expense expense) {
       return new ResponseEntity<>(expenseService.saveExpenseDetails(expense), HttpStatus.CREATED);
    }

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page) {
        return expenseService.getAllExpenses(page).toList();
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id) {
        return expenseService.getExpenseById(id);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetailsById(@PathVariable("id") Long id, @RequestBody Expense expense) {
        return expenseService.updateExpenseDetails(id, expense);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public String deleteExpenseById(@RequestParam("id") Long id) {
        expenseService.deleteExpenseById(id);
        return "Expense deleted successfully!!";
    }
}
