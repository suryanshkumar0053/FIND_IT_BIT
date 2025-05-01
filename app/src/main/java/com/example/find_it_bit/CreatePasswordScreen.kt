package com.example.find_it_bit


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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreatePasswordScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userViewModel: UserViewModel
) {

    val navBackStackEntry = navController.previousBackStackEntry
    val appUser = navBackStackEntry?.savedStateHandle?.get<AppUser>("user")

    val snackbarHostState = remember { SnackbarHostState() }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Create Your Password", fontSize = 22.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (password != confirmPassword) {
                        // show snackbar on UI thread
                        CoroutineScope(Dispatchers.Main).launch {
                            snackbarHostState.showSnackbar("Passwords do not match.")
                        }
                    }  else {
                        // Ensure appUser is not null, and pass its email value
                        val userEmail = appUser?.email ?: ""
                        userViewModel.signupWithEmailAndStoreUser(
                            email = userEmail,  // Pass the email from appUser
                            password = password,
                            user = appUser!!,  // Make sure appUser is not null
                            onResult = { success, message ->
                                if (success) {
                                    // Navigate to home after successful sign-up
                                    navController.navigate("home") {
                                        popUpTo("create_password") { inclusive = true }
                                    }
                                } else {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        snackbarHostState.showSnackbar(message ?: "Something went wrong")
                                    }
                                }
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Account")
            }
        }
    }
}
