package com.example.expense_tracker.service;

import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
    public Optional<Expense> getExpenseById(Long id){
        return expenseRepository.findById(id);
    }
    public List<Expense> getALlExpenses(){
        return expenseRepository.findAll();
    }
    public void deleteExpenseById(Long id){
        expenseRepository.deleteById(id);
    }
    public Expense updateExpense(Long id, Expense updatedExpense){
        return expenseRepository.findById(id)
                .map(existingExpense -> {
                    existingExpense.setName(updatedExpense.getName()); // Update name
                    existingExpense.setAmount(updatedExpense.getAmount());
                    existingExpense.setCategory(updatedExpense.getCategory());
                    existingExpense.setDate(updatedExpense.getDate());
                    return expenseRepository.save(existingExpense);
                })
                .orElseThrow(() ->new RuntimeException("Exception not found with id " + id));
    }
}
