package com.example.typeracer.util

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthManager {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun verify(email: String, complete: () -> Unit, fail: () -> Unit) {
        firebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.result?.signInMethods?.size == 0) {
                    // email not existed
                    complete()
                } else {
                    // email exists
                    fail()
                }
            }
    }

    fun login(email: String?, password: String?, complete: () -> Unit, fail: (String?) -> Unit) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() ) {
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // log in success and logged in automatically
                    complete()
                } else {
                    // if log in fails, display a message to the user
                    fail(task.exception?.message)
                }
            }
            .addOnFailureListener {
                fail(it.message)
            }
    }

    fun signUp(email: String?, password: String?, complete: () -> Unit, fail: (String?) -> Unit) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() ) {
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // sign up success and logged in automatically
                    complete()
                } else {
                    // if sign up fails, display a message to the user
                    fail(task.exception?.message)
                }
            }.addOnFailureListener {
                fail(it.message)
            }
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    fun isUserLoggedIn() = firebaseAuth.currentUser != null

    fun logOut() {
        firebaseAuth.signOut()
    }

}