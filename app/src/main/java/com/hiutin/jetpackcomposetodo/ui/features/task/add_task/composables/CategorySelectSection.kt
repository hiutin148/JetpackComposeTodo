package com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hiutin.jetpackcomposetodo.domain.models.Category
import com.hiutin.jetpackcomposetodo.ui.composables.TouchableOpacity
import com.hiutin.jetpackcomposetodo.ui.theme.AppColors

@Composable
fun CategorySelectSection(
    categories: List<Category>,
    onSelectCategory: (Category) -> Unit,
    selectedCategory: Category?
) {
    Row(
        modifier = Modifier.padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        categories.map {
            val selected = selectedCategory == it
            TouchableOpacity({ onSelectCategory.invoke(it) }) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (selected)
                                it.color
                            else
                                AppColors.grey
                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Text(
                        it.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selected) Color.White else Color.Unspecified,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}