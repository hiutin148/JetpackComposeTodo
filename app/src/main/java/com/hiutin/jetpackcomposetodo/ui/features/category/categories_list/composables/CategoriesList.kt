package com.hiutin.jetpackcomposetodo.ui.features.category.categories_list.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hiutin.jetpackcomposetodo.domain.models.Category

@Composable
fun CategoriesList(
    categories: List<Category>,
    onItemPressed: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category, onItemPressed)
        }
    }
}

