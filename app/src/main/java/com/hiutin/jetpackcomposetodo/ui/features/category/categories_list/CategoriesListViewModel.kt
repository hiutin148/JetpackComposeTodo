package com.hiutin.jetpackcomposetodo.ui.features.category.categories_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiutin.jetpackcomposetodo.domain.models.Category
import com.hiutin.jetpackcomposetodo.domain.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject() constructor(private val categoryRepository: CategoryRepository) :
    ViewModel() {
    val categories: StateFlow<List<Category>> = categoryRepository.getAllCategories().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addCategory(category: Category) {
        try {
            viewModelScope.launch {
                categoryRepository.addCategory(category)
            }
        } catch (e: Exception) {

        }
    }
}