package com.example.expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private boolean success;
    private Object data;

    // Constructor for simple success/failure messages
    public AuthResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.data = null;
    }

    // Constructor for error messages only (success defaults to false)
    public AuthResponse(String message) {
        this.message = message;
        this.success = false;
        this.data = null;
    }

    // Constructor for success responses with data
    public AuthResponse(boolean success, Object data) {
        this.success = success;
        this.data = data;
        this.message = null;
    }
}