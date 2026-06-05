package com.tuempresa.osornomarket.core.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tuempresa.osornomarket.core.model.Product
import com.tuempresa.osornomarket.core.repository.ProductRepository
import kotlinx.coroutines.tasks.await

class FirebaseProductRepository : ProductRepository {
    
    private val db = Firebase.firestore
    private val productsCollection = db.collection("products")

    override suspend fun getAllProducts(): List<Product> {
        return try {
            val result = productsCollection.get().await()
            result.documents.mapNotNull { document ->
                document.toObject(Product::class.java)?.copy(id = document.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addProduct(product: Product): Boolean {
        return try {
            productsCollection.add(product).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateProduct(product: Product): Boolean {
        return try {
            productsCollection.document(product.id).set(product).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteProduct(productId: String): Boolean {
        return try {
            productsCollection.document(productId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
