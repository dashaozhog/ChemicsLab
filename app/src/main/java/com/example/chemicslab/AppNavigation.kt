package com.example.chemicslab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "experiments"
    ) {
        composable("experiments") {
ExperimentsScreen(
                onItemClick = { id -> navController.navigate("details/$id") }
// Тут додайте інші навігаційні колбеки за потреби...
                )

        }
        composable(
            route = "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: /* значення за замовчуванням */
            /* ЕкранДеталей */(
                    itemId = id,
            onNavigateBack = { navController.popBackStack() }
            )
        }
// Тут додайте composable("route") { ... } для решти запланованих екранів...
    }
}
// Приклад заглушкового екрана зі списком
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun /* ЕкранСписку */(onItemClick: (Int) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("/* Назва екрана */") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
// Тут поки що заглушка — реальний список з'явиться в лабораторній No3
            Button(onClick = { onItemClick(/* id елемента */) }) {
                Text("Перейти до деталей")
            }
        }
    }
}
// Приклад заглушкового екрана деталей
@Composable
fun /* ЕкранДеталей */(itemId: Int, onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("/* Назва екрана */") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Text("Деталі елемента з id = $itemId")
            Button(onClick = onNavigateBack) {
                Text("Назад")
            }
        }
    }
}