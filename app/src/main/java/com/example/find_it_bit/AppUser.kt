package com.example.find_it_bit

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppUser(
    val uid: String = "",
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val branch: String = "",
    val batch: String = "",
    val profilePic: Uri? = null,        // For input
    val profilePicUrl: String? = null   // For saved URL
) : Parcelable

