package com.hiutin.jetpackcomposetodo

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.hiutin.jetpackcomposetodo.ui.composables.DismissKeyboardOnTapOutside
import com.hiutin.jetpackcomposetodo.ui.features.MainScreen
import com.hiutin.jetpackcomposetodo.ui.theme.JetpackComposeTodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0
            )
        }
        enableEdgeToEdge()
        setContent {
            DismissKeyboardOnTapOutside {
                JetpackComposeTodoTheme {
                    MainScreen()
                }
            }
        }
    }
}
