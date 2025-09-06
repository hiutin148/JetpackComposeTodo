package com.hiutin.jetpackcomposetodo.ui.features.category.categories_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hiutin.jetpackcomposetodo.R
import com.hiutin.jetpackcomposetodo.ui.composables.AppButton
import com.hiutin.jetpackcomposetodo.ui.features.category.categories_list.composables.AddCategoryBottomSheet
import com.hiutin.jetpackcomposetodo.ui.features.category.categories_list.composables.CategoriesList
import com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables.EmptyComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesListScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesListViewModel = hiltViewModel(),
) {
    val categories = viewModel.categories.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                "Categories",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            if (categories.value.isEmpty()) {
                EmptyComposable(stringResource(R.string.no_category_found))
            } else {
                CategoriesList(categories.value, {})
            }
        }

        AppButton(
            onClick = { showBottomSheet = true },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text(stringResource(R.string.add))
        }
        AddCategoryBottomSheet(
            showBottomSheet,
            onAddCategory = viewModel::addCategory,
            onDismiss = { showBottomSheet = false })
    }
}