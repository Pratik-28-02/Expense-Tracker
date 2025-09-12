package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    Optional<Expense> findByIdAndUser(Long id, User user);
    List<Expense> findByCategoryAndUser(String category, User user);

    @Query("SELECT e FROM Expense e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year AND e.user = :user ORDER BY e.date ASC")
    List<Expense> findByMonthAndYearAndUser(@Param("month") int month, @Param("year") int year, @Param("user") User user);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year AND e.user = :user")
    double calculateTotalByMonthAndUser(@Param("month") int month, @Param("year") int year, @Param("user") User user);
}

