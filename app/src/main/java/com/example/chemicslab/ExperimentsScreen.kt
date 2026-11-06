package com.example.chemicslab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperimentsScreen(onItemClick: (Int) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {Text(stringResource(R.string.app_name)) }

            )
        },

        content = {innerPadding ->
            Box(modifier= Modifier.fillMaxSize().padding(innerPadding)){
                Text(text = "Experiment tab")
            }
            Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
// Тут поки що заглушка — реальний список з'явиться в лабораторній No3
                Button(onClick = { onItemClick(1) }) {
                    Text("Перейти до деталей")
                }
            }
        }
    )

}