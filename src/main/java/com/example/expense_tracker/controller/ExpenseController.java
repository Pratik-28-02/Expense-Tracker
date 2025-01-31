package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> saveExpense(@RequestBody Expense expense) {
        Expense savedExpense = expenseService.saveExpense(expense);
        return  new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getALlExpenses();
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable long id) {
        Optional<Expense> expense = expenseService.getExpenseById(id);
        return expense.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Expense> deleteExpenseById(@PathVariable long id) {
        expenseService.deleteExpenseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id,@RequestBody Expense updatedExpense){
        Expense expense = expenseService.updateExpense(id,updatedExpense);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }
    @GetMapping("/by-month")
    public ResponseEntity<List<Expense>> getExpenseByMonth(@RequestParam int month,@RequestParam int year){
        List<Expense> expense = expenseService.getExpensesByMonth(month,year);
        return new ResponseEntity<>(expense,HttpStatus.OK);
    }
    @GetMapping("/by-category")
    public ResponseEntity<List<Expense>> getByCategory(@RequestParam String category){
        List<Expense> expenses = expenseService.getExpenseByCategory(category);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }
    @GetMapping("/total-by-month")
    public ResponseEntity<Double> totalExpenseByMonth(@RequestParam int month,@RequestParam int year){
        double totalExpense = expenseService.getTotalExpenseByMonth(month,year  );
        return new ResponseEntity<>(totalExpense,HttpStatus.OK);
    }
}
