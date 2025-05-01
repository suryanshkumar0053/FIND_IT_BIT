package com.example.find_it_bit

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import androidx.compose.runtime.State


class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    // Hold user state
    private val _user = mutableStateOf<FirebaseUser?>(auth.currentUser)
    val user: State<FirebaseUser?> = _user

    // Hold OTP verification ID
    private val _verificationId = mutableStateOf<String?>(null)

    // 🔹 Check if user is already logged in
    fun checkAuthStatus() {
        _user.value = auth.currentUser
    }

    init {
        checkAuthStatus()
    }


    // 🔹 Login with Email & Password
    fun loginWithEmail(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    // 🔹 Send OTP for Mobile Authentication
    fun sendOtp(
        phoneNumber: String,
        activity: Activity,
        onCodeSent: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        Log.d("AuthDebug", "sendOtp: Starting OTP process for $phoneNumber")

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d("AuthDebug", "onVerificationCompleted: Auto verification succeeded")
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("AuthDebug", "signInWithCredential: Success")
                                _user.value = auth.currentUser
                            } else {
                                val errorMsg = task.exception?.message ?: "Verification failed"
                                Log.d("AuthDebug", "signInWithCredential: Failed - $errorMsg")
                                onFailure(errorMsg)
                            }
                        }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    val errorMsg = e.message ?: "Verification failed"
                    Log.d("AuthDebug", "onVerificationFailed: $errorMsg")
                    onFailure(errorMsg)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    Log.d("AuthDebug", "onCodeSent: Verification code sent successfully")
                    _verificationId.value = verificationId
                    onCodeSent()
                }
            })
            .build()

        try {
            Log.d("AuthDebug", "verifyPhoneNumber: Attempting to start verification")
            PhoneAuthProvider.verifyPhoneNumber(options)
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Exception during phone number verification"
            Log.d("AuthDebug", "verifyPhoneNumber: Exception - $errorMsg")
            onFailure(errorMsg)
        }
    }


    // 🔹 Verify OTP
    fun verifyOtp(code: String, onComplete: (Boolean, String?) -> Unit) {
        val credential = PhoneAuthProvider.getCredential(_verificationId.value ?: "", code)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    fun sendPasswordResetEmail(
        email: String,
        onResult: (Boolean, String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Reset link sent to $email.")
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Failed to send reset email."
                    onResult(false, errorMessage)
                }
            }
    }
    // Function to send the email verification
    fun sendEmailVerification(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()
            ?.addOnSuccessListener {
                onSuccess()
            }
            ?.addOnFailureListener { exception ->
                onFailure(exception.message ?: "Failed to send email verification")
            }
    }

    // Function to check if the email is verified
    fun checkIfEmailVerified(callback: (Boolean) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.reload()?.addOnCompleteListener {
            callback(user?.isEmailVerified == true)
        }
    }


    // 🔹 Sign Out
    fun signOut() {
        auth.signOut()
        _user.value = null
    }
}
