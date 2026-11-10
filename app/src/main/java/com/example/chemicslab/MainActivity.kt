package com.example.chemicslab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chemicslab.ui.theme.ChemicsLabTheme
import com.example.chemicslab.navigation.AppNavigation
import com.example.chemicslab.ui.theme.ChemicsLabTheme // theme name generated for YOUR project

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChemicsLabTheme {
                AppNavigation()
            }
        }
    }
}
