package com.tuempresa.osornomarket.core.model

/**
 * Representa un Teléfono en el Marketplace.
 * Se mantiene el nombre 'Product' para consistencia con el repositorio, 
 * pero especializado en telefonía.
 */
data class Product(
    val id: String = "",
    val name: String = "",         // Ej: iPhone 13
    val brand: String = "",        // Ej: Apple
    val description: String = "",
    val price: Int = 0,
    val sellerId: String = "",
    val imageUrl: String = "",
    val condition: String = "Nuevo" // Nuevo, Usado, Reacondicionado
)
