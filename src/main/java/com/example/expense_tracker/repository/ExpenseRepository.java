package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    public List<Expense> findByCategory(String category);

    @Query("Select e from Expense e Where MONTH(e.date) = :month AND YEAR(e.date) = :year ORDER BY e.date ASC")
    List<Expense> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("Select sum(e.amount) from Expense e Where MONTH(e.date) = :month AND YEAR(e.date) = :year")
    double calculateTotalByMonth(@Param("month")int month,@Param("year") int year);
}
