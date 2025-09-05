package com.hiutin.jetpackcomposetodo.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    value: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle? = null,
    hint: String = "",
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onTextChange,
            textStyle = textStyle ?: MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        if (value.text.isEmpty()) {
            Text(
                text = hint,
                style = textStyle ?: MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        }
    }
}