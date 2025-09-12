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
            AuthResponse response = new AuthResponse();
            response.setSuccess(true);
            response.setMessage("Expense saved successfully");
            response.setData(savedExpense);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<AuthResponse> getAllExpenses() {
        try {
            List<Expense> expenses = expenseService.getAllExpenses();
            AuthResponse response = new AuthResponse();
            response.setSuccess(true);
            response.setMessage("Expenses retrieved successfully");
            response.setData(expenses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException e) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthResponse> getExpenseById(@PathVariable Long id) {
        try {
            return expenseService.getExpenseById(id)
                    .map(expense -> {
                        AuthResponse response = new AuthResponse();
                        response.setSuccess(true);
                        response.setMessage("Expense found");
                        response.setData(expense);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        AuthResponse response = new AuthResponse();
                        response.setSuccess(false);
                        response.setMessage("Expense not found with id " + id);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (IllegalStateException e) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthResponse> deleteExpenseById(@PathVariable Long id) {
        try {
            expenseService.deleteExpenseById(id);
            AuthResponse response = new AuthResponse();
            response.setSuccess(true);
            response.setMessage("Expense deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException e) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthResponse> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        try {
            Expense expense = expenseService.updateExpense(id, updatedExpense);
            AuthResponse response = new AuthResponse();
            response.setSuccess(true);
            response.setMessage("Expense updated successfully");
            response.setData(expense);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException e) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-month")
    public ResponseEntity<AuthResponse> getExpenseByMonth(@RequestParam int month, @RequestParam int year) {
        try {
            List<Expense> expenses = expenseService.getExpensesByMonth(month, year);
            AuthResponse response = new AuthResponse();
            response.setSuccess(true);
            response.setMessage("Monthly expenses retrieved successfully");
            response.setData(expenses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException e) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/by-category")
    public ResponseEntity<AuthResponse> getByCategory(@RequestParam String category) {
        try {
            List<Expense> expenses = expenseService.getExpenseByCategory(category);
            AuthResponse response = new AuthResponse();
            response.setSuccess(true);
            response.setMessage("Category expenses retrieved successfully");
            response.setData(expenses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException e) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/total-by-month")
    public ResponseEntity<AuthResponse> totalExpenseByMonth(@RequestParam int month, @RequestParam int year) {
        try {
            double totalExpense = expenseService.getTotalExpenseByMonth(month, year);
            AuthResponse response = new AuthResponse();
            response.setSuccess(true);
            response.setMessage("Total monthly expense calculated successfully");
            response.setData(totalExpense);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException e) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}