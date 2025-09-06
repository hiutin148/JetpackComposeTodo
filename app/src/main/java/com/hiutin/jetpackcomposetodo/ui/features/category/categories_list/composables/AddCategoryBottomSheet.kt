package com.hiutin.jetpackcomposetodo.ui.features.category.categories_list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hiutin.jetpackcomposetodo.R
import com.hiutin.jetpackcomposetodo.domain.models.Category
import com.hiutin.jetpackcomposetodo.ui.composables.AppButton
import com.hiutin.jetpackcomposetodo.ui.composables.DismissKeyboardOnTapOutside
import com.hiutin.jetpackcomposetodo.ui.composables.FullColorPickerDialog
import com.hiutin.jetpackcomposetodo.ui.composables.TouchableOpacity
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryBottomSheet(
    showBottomSheet: Boolean,
    onAddCategory: (Category) -> Unit,
    onDismiss: (() -> Unit)? = null,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    if (showBottomSheet) {
        var selectedColor by remember { mutableStateOf(Color.Red) }
        var showDialog by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        if (showDialog) {
            FullColorPickerDialog(
                onDismissRequest = { showDialog = false },
                onColorSelected = { color ->
                    selectedColor = color
                    showDialog = false
                }
            )
        }
        var name by remember { mutableStateOf(TextFieldValue()) }
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onDismiss?.invoke() },
        ) {
            DismissKeyboardOnTapOutside {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_category),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(stringResource(R.string.name)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TouchableOpacity(onClick = { showDialog = true }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(selectedColor, shape = RoundedCornerShape(4.dp))
                                    .clip(RoundedCornerShape(4.dp))
                            )
                            Text(text = stringResource(R.string.choose_color), style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    AppButton(
                        onClick = {
                            val categoryName = name.text
                            if (categoryName.isNotBlank()) {
                                val id = UUID.randomUUID().toString()
                                val newCate = Category(id, categoryName, selectedColor)
                                onAddCategory.invoke(newCate)
                                focusManager.clearFocus()
                                keyboardController?.hide()
                                if (sheetState.isVisible)
                                    scope.launch {
                                        sheetState.hide()
                                    }
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) { Text(stringResource(R.string.save)) }
                }
            }
        }
    }
}