package com.example.find_it_bit

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ItemRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun uploadLostFoundItem(
        item: LostFoundItem,
        imageUris: List<Uri>,
        onResult: (Boolean, String) -> Unit
    ) {
        val docRef = firestore.collection("items").document()
        val itemId = docRef.id
        val uploaderId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val uploadedImageUrls = mutableListOf<String>()

        if (imageUris.isEmpty()) {
            val itemToSave = item.copy(id = itemId, uploaderId = uploaderId)
            docRef.set(itemToSave)
                .addOnSuccessListener { onResult(true, "Item uploaded") }
                .addOnFailureListener { onResult(false, it.message ?: "Failed") }
            return
        }

        val total = imageUris.size
        var uploaded = 0

        imageUris.forEachIndexed { index, uri ->
            val ref = storage.reference.child("item_images/$itemId/image_$index.jpg")
            ref.putFile(uri)
                .continueWithTask { ref.downloadUrl }
                .addOnSuccessListener { url ->
                    uploadedImageUrls.add(url.toString())
                    uploaded++
                    if (uploaded == total) {
                        val itemToSave = item.copy(
                            id = itemId,
                            uploaderId = uploaderId,
                            imageUrls = uploadedImageUrls
                        )
                        docRef.set(itemToSave)
                            .addOnSuccessListener { onResult(true, "Item uploaded") }
                            .addOnFailureListener { onResult(false, it.message ?: "Failed to save item") }
                    }
                }
                .addOnFailureListener {
                    onResult(false, "Image upload failed: ${it.message}")
                }
        }
    }

    fun getItemsByCategory(category: String, onResult: (List<LostFoundItem>) -> Unit) {
        firestore.collection("items")
            .whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { snap ->
                val items = snap.documents.mapNotNull { it.toObject(LostFoundItem::class.java) }
                onResult(items)
            }.addOnFailureListener {
                onResult(emptyList())
            }
    }
}
