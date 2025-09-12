package com.example.expense_tracker.service;

import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.model.User;
import com.example.expense_tracker.repository.ExpenseRepository;
import com.example.expense_tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            throw new IllegalStateException("User not authenticated");
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username));
    }

    public Expense saveExpense(Expense expense) {
        expense.setUser(getCurrentUser());
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        User currentUser = getCurrentUser();
        return expenseRepository.findByUser(currentUser);
    }

    public Optional<Expense> getExpenseById(Long id) {
        User currentUser = getCurrentUser();
        return expenseRepository.findByIdAndUser(id, currentUser);
    }

    public void deleteExpenseById(Long id) {
        User currentUser = getCurrentUser();
        Optional<Expense> expense = expenseRepository.findByIdAndUser(id, currentUser);
        expense.ifPresent(expenseRepository::delete);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        User currentUser = getCurrentUser();
        return expenseRepository.findByIdAndUser(id, currentUser)
                .map(existingExpense -> {
                    existingExpense.setName(updatedExpense.getName());
                    existingExpense.setAmount(updatedExpense.getAmount());
                    existingExpense.setCategory(updatedExpense.getCategory());
                    existingExpense.setDate(updatedExpense.getDate());
                    return expenseRepository.save(existingExpense);
                })
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }

    public List<Expense> getExpensesByMonth(int month, int year) {
        User currentUser = getCurrentUser();
        return expenseRepository.findByMonthAndYearAndUser(month, year, currentUser);
    }

    public List<Expense> getExpenseByCategory(String category) {
        User currentUser = getCurrentUser();
        return expenseRepository.findByCategoryAndUser(category, currentUser);
    }

    public double getTotalExpenseByMonth(int month, int year) {
        User currentUser = getCurrentUser();
        return expenseRepository.calculateTotalByMonthAndUser(month, year, currentUser);
    }
}