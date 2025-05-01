package com.example.find_it_bit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.find_it_bit.ui.theme.FIND_IT_BITTheme
import com.example.find_it_bit.AppNavigation
import com.example.find_it_bit.AuthViewModel
import com.example.find_it_bit.UserViewModel



class MainActivity : ComponentActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Create your repository instance here
        val userRepository = UserRepository() // replace with your actual implementation

        // ✅ ViewModel factories
        val authFactory = AuthViewModelFactory()
        val userFactory = UserViewModelFactory(userRepository)

        // ✅ ViewModel instances
        authViewModel = ViewModelProvider(this, authFactory)[AuthViewModel::class.java]
        userViewModel = ViewModelProvider(this, userFactory)[UserViewModel::class.java]

        enableEdgeToEdge()

        setContent {
            FIND_IT_BITTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel,
                        userViewModel = userViewModel
                    )
                        .let {Modifier.padding(innerPadding)}
                }
            }
        }
    }
}


