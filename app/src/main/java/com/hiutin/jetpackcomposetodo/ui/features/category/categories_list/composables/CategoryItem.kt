package com.hiutin.jetpackcomposetodo.ui.features.category.categories_list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hiutin.jetpackcomposetodo.R
import com.hiutin.jetpackcomposetodo.domain.models.Category
import com.hiutin.jetpackcomposetodo.ui.composables.SwipeToActionItem
import com.hiutin.jetpackcomposetodo.ui.composables.TouchableOpacity

@Composable
fun CategoryItem(
    category: Category, onPressed: (Category) -> Unit, onDelete: (() -> Unit)? = null
) {
    TouchableOpacity({ onPressed.invoke(category) }) {
        SwipeToActionItem(actionWidth = 60.dp, actions = {
            IconButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Red), onClick = { onDelete?.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = Color.White
                )
            }
        }) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp, shape = RoundedCornerShape(4.dp),
                    )
                    .background(Color.White)
                    .padding(all = 8.dp),
                Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = category.color, shape = RoundedCornerShape(4.dp)
                        )
                        .clip(RoundedCornerShape(4.dp))
                )
                with(MaterialTheme.typography.bodyLarge) {
                    Text(
                        text = category.name,
                        style = copy(lineHeight = fontSize, fontWeight = FontWeight.Medium)
                    )
                }
            }
        }
    }

}
