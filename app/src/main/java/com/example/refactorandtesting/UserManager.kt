package com.example.refactorandtesting

// UserManager.kt

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class UserManager {
    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        age: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            onError("All fields must be filled")
            return
        }
        if (!email.contains("@")) {
            onError("Invalid email address")
            return
        }
        if (password.length < 6) {
            onError("Password must be at least 6 characters")
            return
        }
        if (age < 18) {
            onError("User must be at least 18 years old")
            return
        }
        // Save user to database
        val user = User(firstName, lastName, email, password, age)
        Database.saveUser(user)
        // Send welcome email
        EmailService.sendWelcomeEmail(email)
        onSuccess()
    }

    fun authenticateUser(
        email: String,
        password: String,
        onResult: (Boolean) -> Unit
    ) {
        val user = Database.getUserByEmail(email)
        if (user == null) {
            onResult(false)
            return
        }
        onResult(user.password == password)
    }
}
