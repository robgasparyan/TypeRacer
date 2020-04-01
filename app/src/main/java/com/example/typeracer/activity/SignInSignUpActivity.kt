package com.example.typeracer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.typeracer.R
import com.example.typeracer.databinding.ActivitySignInSignUpBinding
import com.example.typeracer.util.FirebaseAuthManager
import com.example.typeracer.viewModel.LoginVM
import org.koin.android.viewmodel.ext.android.viewModel

class SignInSignUpActivity : AppCompatActivity() {

    private val vm by viewModel<LoginVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuthManager.isUserLoggedIn()) {
            // go to home activity if user logged in
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            createActivityVM()
        }
    }

    private fun createActivityVM() {
        DataBindingUtil.setContentView<ActivitySignInSignUpBinding>(
            this, R.layout.activity_sign_in_sign_up
        ).apply {
            this.viewModel = vm
        }
    }
}
