package com.example.find_it_bit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun AppNavigation(
    modifier: Modifier,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "launcher" // Start from launcher to check auth
    ) {
        // Launcher decides: Home or Login
        composable("launcher") {
            LauncherScreen(navController = navController, authViewModel = authViewModel)
        }

        composable("main_login") {
            MainLoginScreen(modifier, navController)
        }

        composable("login_email") {
            LoginWithEmailScreen(modifier, navController, authViewModel)
        }

        composable("login_number") {
            LoginWithNumberScreen(modifier, navController, authViewModel)
        }

        composable("signup") {
            SignupScreen(modifier, navController, authViewModel, userViewModel)
        }

        composable("forgot_password") {
            ForgotPasswordScreen(modifier, navController, authViewModel)
        }

        composable("home") {
            HomeScreen(modifier, navController, authViewModel,userViewModel)
        }

        composable("create_password/{email}") { backStackEntry ->

            CreatePasswordScreen(modifier, navController, userViewModel)
        }
    }
}
@Composable
fun LauncherScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    val user = authViewModel.user.value

    LaunchedEffect(user) {
        delay(1000) // Optional splash delay
        user?.let {
            navController.navigate("home") {
                popUpTo("launcher") { inclusive = true }
            }
        } ?: run {
            navController.navigate("main_login") {
                popUpTo("launcher") { inclusive = true }
            }
        }
    }

    // Show Logo + Spinner while checking
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Optional: set splash background color
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 🔻 Replace with your logo
            Image(
                painter = painterResource(id = R.drawable.profilepicture), // Add your logo in res/drawable
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CircularProgressIndicator()
        }
    }
}
