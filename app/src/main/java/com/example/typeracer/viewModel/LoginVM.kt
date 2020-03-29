package com.example.typeracer.viewModel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.databinding.ObservableField
import android.util.Patterns
import android.widget.Toast
import com.example.typeracer.R
import com.example.typeracer.TypeRacerApplication
import com.example.typeracer.activity.MainActivity
import com.example.typeracer.util.FirebaseAuthManager
import java.util.regex.Pattern

class LoginVM(app: Application) : BaseVM(app) {

    val email = ObservableField("")
    val password = ObservableField("")
    val emailError = ObservableField<String>()
    val passwordError = ObservableField<String>()
    val showProgress = ObservableField(false)

    private val firebaseAuthManager = FirebaseAuthManager

    fun loginOrRegister() {
        if (isValid()) {
            showProgressDialog()
            firebaseAuthManager.verify(email.get()!!, { /*email.get() in this case can't be null, because already checked in isValid()*/
                registerNewUser()
            }, {
                login()
            })
        }
    }

    private fun openMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent)
    }

    private fun registerNewUser() {
        val appContext = getApplication<TypeRacerApplication>().applicationContext
        firebaseAuthManager.signUp(email.get(), password.get(), {
            dismissProgressBar()
            openMainActivity(appContext)
        }, {
            dismissProgressBar()
            Toast.makeText(appContext, R.string.registration_failed, Toast.LENGTH_LONG).show()
            emailError.set(it)
        })
    }

    private fun login() {
        val appContext = getApplication<TypeRacerApplication>().applicationContext
        firebaseAuthManager.login(email.get(), password.get(), {
            dismissProgressBar()
            openMainActivity(appContext)
        }, {
            dismissProgressBar()
            Toast.makeText(appContext, R.string.log_in_failed, Toast.LENGTH_LONG).show()
            passwordError.set(it)
        })
    }

    private fun isValid() = isValidEmail(email.get()) && isValidPassword(password.get())

    /**
     * Returns is given email valid or not using Android standard EMAIL_ADDRESS pattern
     * Fills error form
     */
    private fun isValidEmail(email: String?): Boolean {
        return if (email.isNullOrEmpty()) {
            emailError.set("You must fill email field")
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError.set("Invalid email address")
            false
        } else {
            true
        }
    }

    /**
     * Returns is given password valid or not
     * Fills error form
     *
     * (?=.*[0-9])      - a digit must occur at least once
     * (?=.*[a-z])      - a lower case letter must occur at least once
     * (?=.*[A-Z])      - an upper case letter must occur at least once
     * (?=\\S+$)        - no whitespace allowed in the entire password
     * .{6,}            - anything, at least 6 places though
     */
    private fun isValidPassword(password: String?): Boolean {
        if (password.isNullOrEmpty()) {
            passwordError.set(getString(R.string.password_can_not_be_empty))
            return false
        }
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$"
        val pattern = Pattern.compile(passwordPattern)
        val matcher = pattern.matcher(password)

        val isValid = matcher.matches()
        return isValid.also {
            if (!isValid) {
                passwordError.set(getString(R.string.password_requirements))
            }
        }
    }

    private fun showProgressDialog() {
        showProgress.set(true)
    }

    private fun dismissProgressBar() {
        showProgress.set(false)
    }
}