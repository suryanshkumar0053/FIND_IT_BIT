package com.example.find_it_bit

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*

@Composable
fun LoginWithNumberScreen(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var isOtpSent by remember { mutableStateOf(false) }
    var isSendingOtp by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val activity = context as Activity

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                snackbar = { data ->
                    Snackbar(
                        snackbarData = data,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login with Phone", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            if (isOtpSent) {
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = { Text("Enter OTP") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        authViewModel.verifyOtp(otp) { success, message ->
                            if (success) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    snackbarHostState.showSnackbar("Login Successful ✅")
                                    delay(1500)
                                    navController.navigate("home") {
                                        popUpTo("main_login") { inclusive = true }
                                    }
                                }
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Verify OTP")
                }

            } else {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (!isSendingOtp) {
                            isSendingOtp = true
                            authViewModel.sendOtp(phoneNumber, activity, {
                                isOtpSent = true
                                isSendingOtp = false
                            }) { error ->
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                isSendingOtp = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSendingOtp
                ) {
                    Text(if (isSendingOtp) "Sending OTP..." else "Send OTP")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Back")
                }

                TextButton(onClick = { navController.navigate("signup") }) {
                    Text("Sign Up")
                }
            }
        }
    }
}
