package com.example.find_it_bit

data class LostFoundItem(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "", // "Lost" or "Found"
    val date: Long = System.currentTimeMillis(),
    val location: String = "",
    val imageUrls: List<String> = emptyList(),
    val uploaderId: String = ""
)
