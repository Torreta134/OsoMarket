package com.tuempresa.osornomarket.features.product_detail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tuempresa.osornomarket.core.model.Product
import com.tuempresa.osornomarket.features.product_detail.viewmodel.ProductDetailState
import com.tuempresa.osornomarket.features.product_detail.viewmodel.ProductDetailViewModel

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProductScreen(
    productId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: ProductDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    // Si la operación fue exitosa, volvemos atrás
    LaunchedEffect(uiState) {
        if (uiState is ProductDetailState.Success) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (productId == null) "Agregar Teléfono" else "Editar Teléfono") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del Modelo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = brand,
                onValueChange = { brand = it },
                label = { Text("Marca") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val product = Product(
                        id = productId ?: "",
                        name = name,
                        brand = brand,
                        price = price.toIntOrNull() ?: 0
                    )
                    viewModel.saveProduct(product)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is ProductDetailState.Loading
            ) {
                if (uiState is ProductDetailState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Guardar Teléfono")
                }
            }

            if (productId != null) {
                TextButton(
                    onClick = { viewModel.deleteProduct(productId) },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Eliminar Producto")
                }
            }
        }
    }
}
