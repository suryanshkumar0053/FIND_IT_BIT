package com.example.find_it_bit


import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
    val firebaseUser = authViewModel.user.value
    val appUser = userViewModel.userData.value
    val context = LocalContext.current

    // 🔄 Fetch Firestore data when screen opens
    LaunchedEffect(firebaseUser?.uid) {
        firebaseUser?.uid?.let { userViewModel.fetchUserData(it) }
    }

    BackHandler {
        (context as? Activity)?.finish()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        if (appUser != null) {
            // ✅ Show user info
            UserCard(user = appUser)
        } else {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            authViewModel.signOut()
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }) {
            Text("Sign Out")
        }
    }
}


@Composable
fun UserCard(user: AppUser) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if ((user.profilePicUrl ?: "").isNotEmpty()) {
                AsyncImage(
                    model = user.profilePicUrl,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text("Name: ${user.name}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Email: ${user.email}", fontSize = 16.sp)
                Text("Phone: ${user.phone}", fontSize = 16.sp)
                Text("Branch: ${user.branch}", fontSize = 16.sp)
                Text("Batch: ${user.batch}", fontSize = 16.sp)
            }
        }
    }
}
