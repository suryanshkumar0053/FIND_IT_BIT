package com.example.find_it_bit

import android.app.Activity
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope


@Composable
fun SignupScreen(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel,userViewModel: UserViewModel) {
    val context = LocalContext.current
    val activity = context as Activity
    val lifecycleOwner = LocalLifecycleOwner.current

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var name by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var batch by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }

    var isEmailVerified by remember { mutableStateOf(false) }
    var isVerificationEmailSent by remember { mutableStateOf(false) }
    var isCheckingVerification by remember { mutableStateOf(false) }
    var verificationStartTime by remember { mutableStateOf(0L) }


    var isMobileNumberVerified by remember { mutableStateOf(false) }

    var showOtpDialog by remember { mutableStateOf(false) }

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showImagePickerDialog by remember { mutableStateOf(false) }


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) profileImageUri = uri
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            // Save the bitmap safely and get a Uri
            lifecycleOwner.lifecycleScope.launch {
                val uri = userViewModel.saveBitmapToGallery(context, it, "profile_pic")
                uri?.let { safeUri ->
                    profileImageUri = safeUri
                }
            }
        }


}

    // Launch coroutine to check email verification status after sending the email
    LaunchedEffect(isCheckingVerification) {
        if (isCheckingVerification) {
            val startTime = System.currentTimeMillis()
            verificationStartTime = startTime

            // Periodically check email verification status every 5 seconds until timeout or success
            while (!isEmailVerified && System.currentTimeMillis() - startTime < 60000) { // Timeout after 1 minute
                delay(5000) // 5 seconds delay for auto-check

                // Check if the email is verified
                authViewModel.checkIfEmailVerified { verified ->
                    if (verified) {
                        isEmailVerified = true
                        isCheckingVerification = false // Stop checking once verified
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Email verified successfully!")
                        }
                    }
                }
            }

            // If the email is not verified after 1 minute, show timeout message
            if (!isEmailVerified && System.currentTimeMillis() - startTime >= 60000) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Email not verified. Please try again.")
                }
                isCheckingVerification = false // Stop checking after timeout
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .padding(padding)
        ) {
            Text(
                text = "Signup Details",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(130.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { showImagePickerDialog = true },
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = profileImageUri ?: R.drawable.profilepicture
                    ),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFF25D366), CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                        .clickable { showImagePickerDialog = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Camera Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            if (showImagePickerDialog) {
                AlertDialog(
                    onDismissRequest = { showImagePickerDialog = false },
                    title = { Text("Choose Image") },
                    text = {
                        Column {
                            Text(
                                text = "Select from Gallery",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        galleryLauncher.launch("image/*")
                                        showImagePickerDialog = false
                                    }
                                    .padding(12.dp)
                            )
                            HorizontalDivider()

                            Text(
                                text = "Take Photo",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        cameraLauncher.launch(null)
                                        showImagePickerDialog = false
                                    }
                                    .padding(12.dp)
                            )
                        }
                    },
                    confirmButton = {},
                    dismissButton = {
                        TextButton(onClick = { showImagePickerDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            SignupRowField(label = "Name", value = name, onValueChange = { name = it })
            SignupRowField(label = "Branch", value = branch, onValueChange = { branch = it })
            SignupRowField(label = "Batch", value = batch, onValueChange = { batch = it })

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PhoneRowField(
                        label = "Phone",
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        modifier = Modifier.weight(1f)
                    )

                    if (!isMobileNumberVerified) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            if (!isValidPhoneNumber(phoneNumber)) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Enter a valid 10-digit Indian phone number")
                                }
                                return@Button
                            }

                            Log.d("SignupScreen", "Sending OTP to: $phoneNumber")

                            val fullPhoneNumber = "+91$phoneNumber"

                            authViewModel.sendOtp(
                                phoneNumber = fullPhoneNumber,
                                activity = activity,
                                onCodeSent = {
                                    Log.d("SignupScreen", "OTP sent successfully")
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("OTP sent successfully")
                                    }
                                    showOtpDialog = true
                                },
                                onFailure = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                    Log.d("SignupScreen", "OTP send failed: fuck you")
                                }
                            )
                        }) {
                            Text("Verify")
                        }
                    }
                }

                if (showOtpDialog) {
                    AlertDialog(
                        onDismissRequest = { showOtpDialog = false },
                        title = { Text("Enter OTP") },
                        text = {
                            Column {
                                TextField(
                                    value = otp,
                                    onValueChange = { otp = it },
                                    label = { Text("OTP") },
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                Log.d("SignupScreen", "Verifying OTP...")

                                authViewModel.verifyOtp(otp) { verified, msg ->
                                    if (verified) {
                                        isMobileNumberVerified = true
                                        Log.d("SignupScreen", "OTP verified successfully")

                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Mobile number verified!")
                                        }

                                        showOtpDialog = false
                                    } else {
                                        Log.d("SignupScreen", "OTP verification failed: $msg")
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(msg ?: "Invalid OTP")
                                        }
                                    }
                                }
                            }) {
                                Text("Verify")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showOtpDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SignupRowField(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.weight(1f)
                    )

                    if (!isEmailVerified && !isVerificationEmailSent) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Please enter a valid email address.")
                                }
                                return@Button
                            }

                            authViewModel.sendEmailVerification(
                                onSuccess = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Verification email sent. Please check your inbox.")
                                    }
                                    isVerificationEmailSent = true
                                    isCheckingVerification = true
                                },
                                onFailure = { error ->
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Error: $error")
                                    }
                                }
                            )
                        }) {
                            Text("Verify Email")
                        }
                    }

                    if (isVerificationEmailSent && !isEmailVerified) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            authViewModel.checkIfEmailVerified { verified ->
                                isEmailVerified = verified
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        if (verified) "Email verified!" else "Email not verified yet."
                                    )
                                }
                            }
                        }) {
                            Text("Check Verification")
                        }
                    }
                }

                if (!isEmailVerified && isVerificationEmailSent && System.currentTimeMillis() - verificationStartTime >= 60000) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        authViewModel.sendEmailVerification(
                            onSuccess = {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Verification email resent. Please check your inbox.")
                                }
                                isVerificationEmailSent = true
                                isCheckingVerification = true
                            },
                            onFailure = { error ->
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Error: $error")
                                }
                            }
                        )
                    }) {
                        Text("Verify Email Again")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                if (name.isBlank() || branch.isBlank() || batch.isBlank() || email.isBlank() || phoneNumber.isBlank()) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Please fill all the fields before proceeding.")
                    }
                    return@Button
                }

                if (isEmailVerified && isMobileNumberVerified) {
                    val appUser = AppUser(
                        uid = "",
                        name = name,
                        phone = phoneNumber,
                        email = email,
                        branch = branch,
                        batch = batch,
                        profilePic = profileImageUri
                    )
                    navController.currentBackStackEntry?.savedStateHandle?.set("user", appUser)
                    navController.navigate("create_password_screen")
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Please verify your email and mobile number first.")
                    }
                }
            }) {
                Text("Create Password")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TextButton(onClick = {
                    navController.navigate("main_login") {
                        popUpTo("signup") { inclusive = true }
                    }
                }) {
                    Text("Already have an account? Login")
                }
            }
        }
    }

}



// Composable for form fields with label and text input
@Composable
fun SignupRowField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
)  {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            modifier = Modifier.width(90.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PhoneRowField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
)  {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            modifier = Modifier.width(90.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            value = value,
            onValueChange = {
                if (it.length <= 10 && it.all { ch -> ch.isDigit() }) {
                    onValueChange(it)
                }
            },
            leadingIcon = {
                Text("+91", fontSize = 16.sp, modifier = Modifier.padding(start = 4.dp))
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun isValidPhoneNumber(number: String): Boolean {
    return number.matches(Regex("^[6-9]\\d{9}$"))
}




