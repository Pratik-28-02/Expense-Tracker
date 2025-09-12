package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.AuthResponse;
import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> saveExpense(@RequestBody Expense expense) {
        try {
            Expense savedExpense = expenseService.saveExpense(expense);
            return new ResponseEntity<>(new AuthResponse(true, savedExpense), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<AuthResponse> getAllExpenses() {
        try {
            List<Expense> expenses = expenseService.getAllExpenses();
            return new ResponseEntity<>(new AuthResponse(true, expenses), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthResponse> getExpenseById(@PathVariable Long id) {
        try {
            return expenseService.getExpenseById(id)
                    .map(expense -> new ResponseEntity<>(new AuthResponse(true, expense), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(new AuthResponse("Expense not found with id " + id), HttpStatus.NOT_FOUND));
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthResponse> deleteExpenseById(@PathVariable Long id) {
        try {
            expenseService.deleteExpenseById(id);
            return new ResponseEntity<>(new AuthResponse(true, null), HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthResponse> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        try {
            Expense expense = expenseService.updateExpense(id, updatedExpense);
            return new ResponseEntity<>(new AuthResponse(true, expense), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-month")
    public ResponseEntity<AuthResponse> getExpenseByMonth(@RequestParam int month, @RequestParam int year) {
        try {
            List<Expense> expenses = expenseService.getExpensesByMonth(month, year);
            return new ResponseEntity<>(new AuthResponse(true, expenses), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/by-category")
    public ResponseEntity<AuthResponse> getByCategory(@RequestParam String category) {
        try {
            List<Expense> expenses = expenseService.getExpenseByCategory(category);
            return new ResponseEntity<>(new AuthResponse(true, expenses), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/total-by-month")
    public ResponseEntity<AuthResponse> totalExpenseByMonth(@RequestParam int month, @RequestParam int year) {
        try {
            double totalExpense = expenseService.getTotalExpenseByMonth(month, year);
            return new ResponseEntity<>(new AuthResponse(true, totalExpense), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}