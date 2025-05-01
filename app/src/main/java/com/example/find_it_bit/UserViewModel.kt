package com.example.find_it_bit

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import androidx.compose.runtime.State


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userData = mutableStateOf<AppUser?>(null)
    val userData: State<AppUser?> = _userData

    fun signupWithEmailAndStoreUser(
        email: String,
        password: String,
        user: AppUser,  // appUser containing user details like name, phone, and profile picture
        onResult: (success: Boolean, message: String?) -> Unit
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Step 2: Get the UID after successful registration
                    val uid = FirebaseAuth.getInstance().currentUser?.uid

                    if (uid != null) {
                        // Step 3: Link the phone number to the user's Firebase account (if available)

                            linkPhoneNumberToUser(user.phone, uid, task.result.user, user, onResult)

                    } else {
                        // Return failure if UID is null
                        onResult(false, "Failed to get user UID")
                    }
                } else {
                    // Return failure if Firebase Auth failed
                    onResult(false, task.exception?.message)
                }
            }
    }

    private fun linkPhoneNumberToUser(
        phoneNumber: String,
        uid: String,
        firebaseUser: FirebaseUser?,
        user: AppUser,
        onResult: (success: Boolean, message: String?) -> Unit
    ) {
        // If phone number is already verified and you have a valid phone credential, link it directly
        val phoneAuthCredential = PhoneAuthProvider.getCredential(phoneNumber, "validVerificationCode")

        firebaseUser?.linkWithCredential(phoneAuthCredential)
            ?.addOnCompleteListener { linkTask ->
                if (linkTask.isSuccessful) {
                    // If linking is successful, save user data
                    saveUserData(uid, user, onResult)
                } else {
                    onResult(false, "Failed to link phone number")
                }
            }
    }
    private fun saveUserData(uid: String, user: AppUser, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            // Step 1: Upload profile picture to storage if provided
            val profilePicUrl = user.profilePic?.let {
                userRepository.uploadProfilePictureToStorage(it, uid)
            }

            // Step 2: Pass the user with updated profilePicUrl to repository
            val updatedUser = user.copy(profilePicUrl = profilePicUrl)

            userRepository.saveUserToFirestore(uid, updatedUser, onResult)
        }
    }


     fun saveBitmapToGallery(context: Context, bitmap: Bitmap, displayName: String): Uri? {
        val resolver = context.contentResolver

        val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val newImage = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val imageUri = resolver.insert(imageCollection, newImage)

        imageUri?.let { uri ->
            resolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                newImage.clear()
                newImage.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(uri, newImage, null, null)
            }
        }

        return imageUri
    }

    fun fetchUserData(uid: String) {
        userRepository.fetchUserData(
            uid = uid,
            onResult = { user ->
                _userData.value = user
            },
            onError = { e ->
                Log.e("UserViewModel", "Error fetching user data", e)
            }
        )
    }
}