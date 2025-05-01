package com.example.find_it_bit

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
) {
    suspend fun uploadProfilePictureToStorage(profileUri: Uri, uid: String): String? = withContext(Dispatchers.IO) {
        try {
            val storageRef = FirebaseStorage.getInstance().reference
                .child("profile_pics/$uid/profile_pic.jpg")
            storageRef.putFile(profileUri).await()
            val downloadUrl = storageRef.downloadUrl.await()
            return@withContext downloadUrl.toString()
        } catch (e: Exception) {
            Log.e("UPLOAD_ERROR", "Failed to upload image: ${e.message}")
            return@withContext null
        }
    }


    fun saveUserToFirestore(
        uid: String,
        user: AppUser,
        onResult: (Boolean, String?) -> Unit
    ) {
        val userMap = hashMapOf(
            "name" to user.name,
            "branch" to user.branch,
            "batch" to user.batch,
            "email" to user.email,
            "phone" to user.phone,
            "profilePicUrl" to (user.profilePicUrl ?: "")
        )

        firestore.collection("users").document(uid)
            .set(userMap)
            .addOnSuccessListener { onResult(true, "Sign up successful!") }
            .addOnFailureListener { onResult(false, it.message) }
    }

    fun fetchUserData(uid: String, onResult: (AppUser?) -> Unit, onError: (Exception) -> Unit) {
        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(AppUser::class.java)
                    onResult(user)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

}

